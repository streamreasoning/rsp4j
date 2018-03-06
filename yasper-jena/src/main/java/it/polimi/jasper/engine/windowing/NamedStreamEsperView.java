package it.polimi.jasper.engine.windowing;

import it.polimi.jasper.engine.spe.content.ContentBean;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NamedStreamEsperView extends EsperStatementView {

    private WindowAssigner woa;
    private String uri;

    public NamedStreamEsperView(String uri, Maintenance maintenance, WindowAssigner wo) {
        super(maintenance);
        this.woa = wo;
        this.uri = uri;
    }

    @Override
    public void update(long t) {
        ContentBean content = (ContentBean) woa.getContent(t);
        eval(null, content.asArray(), t);
    }

}