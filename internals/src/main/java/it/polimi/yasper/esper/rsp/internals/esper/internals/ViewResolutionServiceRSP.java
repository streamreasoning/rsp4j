package it.polimi.yasper.esper.rsp.internals.esper.internals;

import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.spec.PluggableObjectEntry;
import com.espertech.esper.epl.spec.PluggableObjectRegistry;
import com.espertech.esper.epl.spec.PluggableObjectType;
import com.espertech.esper.epl.virtualdw.VirtualDWViewFactoryImpl;
import com.espertech.esper.view.ViewFactory;
import com.espertech.esper.view.ViewProcessingException;
import com.espertech.esper.view.ViewResolutionService;
import com.espertech.esper.view.ViewResolutionServiceImpl;
import it.polimi.rspql.RSPEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by riccardo on 02/09/2017.
 */
public class ViewResolutionServiceRSP implements ViewResolutionService {
    private static final Logger log = LoggerFactory.getLogger(ViewResolutionServiceImpl.class);

    private final PluggableObjectRegistry viewObjects;
    private final String optionalNamedWindowName;
    private Class virtualDataWindowViewFactoryClass;
    private ViewFactory virtualDataWindowViewFactory;

    public ViewResolutionServiceRSP(PluggableObjectRegistry viewObjects, String optionalNamedWindowName, Class virtualDataWindowViewFactory) {
        this.viewObjects = viewObjects;
        this.optionalNamedWindowName = optionalNamedWindowName;
        this.virtualDataWindowViewFactoryClass = virtualDataWindowViewFactory;
    }

    public ViewResolutionServiceRSP(PluggableObjectRegistry viewObjects, String optionalNamedWindowName, ViewFactory virtualDataWindowViewFactory, RSPEngine engine) {
        this.viewObjects = viewObjects;
        this.optionalNamedWindowName = optionalNamedWindowName;
        this.virtualDataWindowViewFactory = virtualDataWindowViewFactory;
    }

    public ViewFactory create(String nameSpace, String name) throws ViewProcessingException {
        if (log.isDebugEnabled()) {
            log.debug(".create Creating view factory, namespace=" + nameSpace + " name=" + name);
        }

        if (virtualDataWindowViewFactory == null) {

            Pair<Class, PluggableObjectEntry> pair = viewObjects.lookup(nameSpace, name);
            if (pair != null) {
                if (pair.getSecond().getType() == PluggableObjectType.VIEW) {
                    // Handle named windows in a configuration that always declares a system-wide virtual view factory
                    if (optionalNamedWindowName != null && virtualDataWindowViewFactoryClass != null) {
                        return new VirtualDWViewFactoryImpl(virtualDataWindowViewFactoryClass, optionalNamedWindowName, null);
                    }

                    virtualDataWindowViewFactoryClass = pair.getFirst();
                } else if (pair.getSecond().getType() == PluggableObjectType.VIRTUALDW) {
                    if (optionalNamedWindowName == null) {
                        throw new ViewProcessingException("Virtual data window requires use with a named window in the create-window syntax");
                    }
                    return new VirtualDWViewFactoryImpl(pair.getFirst(), optionalNamedWindowName, pair.getSecond().getCustomConfigs());
                } else {
                    throw new ViewProcessingException("Invalid object type '" + pair.getSecond() + "' for view '" + name + "'");
                }
            }

            if (virtualDataWindowViewFactoryClass == null) {
                String message = nameSpace == null ?
                        "View name '" + name + "' is not a known view name" :
                        "View name '" + nameSpace + ":" + name + "' is not a known view name";
                throw new ViewProcessingException(message);
            }

            try {
                virtualDataWindowViewFactory = (ViewFactory) virtualDataWindowViewFactoryClass.newInstance();

                if (log.isDebugEnabled()) {
                    log.debug(".create Successfully instantiated view");
                }
            } catch (ClassCastException e) {
                String message = "Error casting view factory instance to " + ViewFactory.class.getName() + " interface for view '" + name + "'";
                throw new ViewProcessingException(message, e);
            } catch (IllegalAccessException e) {
                String message = "Error invoking view factory constructor for view '" + name;
                message += "', no invocation access for Class.newInstance";
                throw new ViewProcessingException(message, e);
            } catch (InstantiationException e) {
                String message = "Error invoking view factory constructor for view '" + name;
                message += "' using Class.newInstance";
                throw new ViewProcessingException(message, e);
            }

        }

        return virtualDataWindowViewFactory;
    }
}
