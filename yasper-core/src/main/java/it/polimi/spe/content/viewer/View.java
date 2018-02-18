package it.polimi.spe.content.viewer;

import java.util.Observable;

public interface View {
    void addObservable(Observable windowAssigner);
}
