package com.nordea.frontend.ui;

import com.fasterxml.jackson.databind.JsonNode;
import com.nordea.frontend.data.RestClientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Fetch all countries view")
@Route(value = "fetch-all-countries", layout = MainLayout.class)
public class FetchAllCountriesView extends Main {

    public FetchAllCountriesView(@Autowired RestClientService service) {
        // The second example does not use a DTO, but raw JSON instead using the Jackson
        // library included with Spring. The data is a List of JSON nodes; for each
        // column, we define how to get the correct data from the node.

        // This is useful when the REST API returns dynamic data
        final Grid<JsonNode> countriesGridForInHouseSource = new Grid<JsonNode>();
        countriesGridForInHouseSource.setHeight("200px");

        countriesGridForInHouseSource.addColumn(node -> node.get("name").asText()).setHeader("Name");
        countriesGridForInHouseSource.addColumn(node -> node.get("country_code").asText()).setHeader("Country Code");
        final Button fetchCountriesForInHouseSourceButton = new Button("Fetch all countries (in-house source)", e -> countriesGridForInHouseSource.setItems(service.getAllCountries()));
        fetchCountriesForInHouseSourceButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        VerticalLayout vlForInHouseSource = new VerticalLayout();
        vlForInHouseSource.add(fetchCountriesForInHouseSourceButton);
        vlForInHouseSource.add(countriesGridForInHouseSource);

        final Grid<JsonNode> countriesGridForThirdPartySource = new Grid<JsonNode>();

        countriesGridForThirdPartySource.addColumn(node -> node.get("name").asText()).setHeader("Name");
        countriesGridForThirdPartySource.addColumn(node -> node.get("country_code").asText()).setHeader("Country Code");

        // Fetch all data and show
        final Button fetchCountriesFromThirdPartyButton = new Button("Fetch all countries from third party", e -> countriesGridForThirdPartySource.setItems(service.getAllCountriesFromThirdParty()));
        fetchCountriesFromThirdPartyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        VerticalLayout vlForThirdPartySource = new VerticalLayout();
        vlForThirdPartySource.add(fetchCountriesFromThirdPartyButton);
        vlForThirdPartySource.add(countriesGridForThirdPartySource);


        add(vlForInHouseSource, vlForThirdPartySource);

    }
}