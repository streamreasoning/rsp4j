package it.polimi.jasper.engine.windowing;

import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
public class NamedStreamEsperView extends StreamEsperView {

    private String uri;

    public NamedStreamEsperView(String uri, Maintenance maintenance, WindowAssigner wo) {
        super(maintenance, wo);
        this.uri = uri;
    }

}