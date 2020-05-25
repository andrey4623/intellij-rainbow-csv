package com.andrey4623.rainbowcsv.settings;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Component;
import java.util.function.Function;

public class CustomListCellRenderer<E> implements ListCellRenderer<E> {

    private final ListCellRenderer listCellRenderer;
    private final Function<E, String> getName;

    public CustomListCellRenderer(Function<E, String> getName) {
        this(new DefaultListCellRenderer(), getName);
    }

    public CustomListCellRenderer(ListCellRenderer listCellRenderer, Function<E, String> getName) {
        this.listCellRenderer = listCellRenderer;
        this.getName = getName;
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends E> list, E value, int index, boolean isSelected, boolean cellHasFocus
    ) {
        return listCellRenderer.getListCellRendererComponent(
                list, getName.apply(value), index, isSelected, cellHasFocus
        );
    }
}
