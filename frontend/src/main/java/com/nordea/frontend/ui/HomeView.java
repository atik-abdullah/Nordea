package com.nordea.frontend.ui;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Vaadin REST Examples")
@Route(value = "", layout = MainLayout.class)
public class HomeView extends Main {
    public HomeView() {
        add(new Section(new Paragraph(
                "This example app demonstrates how to call REST services. It fetches data from two different sources "
                        + "and shows the results in a Vaadin Grid."),
                new Span("The sources for this application can be found "),
                new Anchor("https://github.com/atik-abdullah/Nordea", "here.")));
    }
}