package com.nordea.frontend.ui;

import com.fasterxml.jackson.databind.JsonNode;
import com.nordea.frontend.data.RestClientService;
import com.nordea.frontend.exception.CustomErrorHandler;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Fetch country by name")
@Route(value = "fetch-country-by-name", layout = MainLayout.class)
public class FetchCountryByNameView extends Main {

    public FetchCountryByNameView(@Autowired RestClientService service) {
        VaadinSession.getCurrent().setErrorHandler(new CustomErrorHandler());

        final Grid<JsonNode> countriesGridForInHouseSource = new Grid<>();
        countriesGridForInHouseSource.setHeight("200px");

        countriesGridForInHouseSource.addColumn(node -> node.get("name").asText()).setHeader("Name");
        countriesGridForInHouseSource.addColumn(node -> node.get("country_code").asText()).setHeader("Country Code");
        countriesGridForInHouseSource.addColumn(node -> node.get("capital").asText()).setHeader("Capital");
        countriesGridForInHouseSource.addColumn(node -> node.get("population").asText()).setHeader("Population");

        TextField nameFieldForInHouseSource = new TextField();
        Button fetchCountryByNameForInHouseButton = new Button("Fetch country by name (in-house source)");
        fetchCountryByNameForInHouseButton.addClickListener(click -> {
            countriesGridForInHouseSource.setItems(service.getCountryByName(nameFieldForInHouseSource.getValue()));
        });
        fetchCountryByNameForInHouseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout hlForInHouseSource = new HorizontalLayout();
        hlForInHouseSource.add(nameFieldForInHouseSource);
        hlForInHouseSource.add(fetchCountryByNameForInHouseButton);

        VerticalLayout vlForInHouseSource = new VerticalLayout();
        vlForInHouseSource.add(hlForInHouseSource, countriesGridForInHouseSource );

        final Grid<JsonNode> countriesGridForThirdPartySource = new Grid<>();
        countriesGridForThirdPartySource.setHeight("200px");

        countriesGridForThirdPartySource.addColumn(node -> node.get(0).get("name").asText()).setHeader("Name");
        countriesGridForThirdPartySource.addColumn(node -> node.get(0).get("country_code").asText()).setHeader("Country Code");
        countriesGridForThirdPartySource.addColumn(node -> node.get(0).get("capital").asText()).setHeader("Capital");
        countriesGridForThirdPartySource.addColumn(node -> node.get(0).get("population").asText()).setHeader("Population");

        TextField nameFieldForThirPartySource = new TextField();
        Button fetchCountryByNameButtonForThirdParty = new Button("Fetch country by name (third party source)");
        fetchCountryByNameButtonForThirdParty.addClickListener(click -> {
            countriesGridForThirdPartySource.setItems(service.getCountryByNameFromThirdParty(nameFieldForThirPartySource.getValue()));
        });
        fetchCountryByNameButtonForThirdParty.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout hlForThirdPartySource = new HorizontalLayout();
        hlForThirdPartySource.add(nameFieldForThirPartySource);
        hlForThirdPartySource.add(fetchCountryByNameButtonForThirdParty);
        VerticalLayout vlForThirdPartySource = new VerticalLayout();
        vlForThirdPartySource.add(hlForThirdPartySource, countriesGridForThirdPartySource );

        add(vlForInHouseSource, vlForThirdPartySource);

    }
}