package it.polimi.jasper.engine.windowing;

import it.polimi.jasper.engine.spe.content.ContentBean;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import lombok.extern.log4j.Log4j;

@Log4j
public class StreamEsperView extends EsperStatementView {

    private WindowAssigner woa;

    public StreamEsperView(Maintenance maintenance, WindowAssigner wo) {
        super(maintenance);
        this.woa = wo;
    }

    @Override
    public void update(long t) {
        ContentBean content = (ContentBean) woa.getContent(t);
        eval(null, content.asArray(), t);
    }


}