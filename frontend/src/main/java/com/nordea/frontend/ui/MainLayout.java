package com.nordea.frontend.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLink;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout implements AfterNavigationObserver {

    private final H1 pageTitle;
    private final RouterLink homeView;
    private final RouterLink fetchCountryByNameView;
    private final RouterLink fetchAllCountryView;

    public MainLayout() {
        // Navigation
        homeView = new RouterLink("Home", HomeView.class);
        fetchCountryByNameView = new RouterLink("Fetch Country By Name", FetchCountryByNameView.class);
        fetchAllCountryView = new RouterLink("Fetch All Countries", FetchAllCountriesView.class);

        final UnorderedList list = new UnorderedList(new ListItem(homeView), new ListItem(fetchCountryByNameView), new ListItem(fetchAllCountryView));
        final Nav navigation = new Nav(list);
        addToDrawer(navigation);
        setPrimarySection(Section.DRAWER);
        setDrawerOpened(false);

        // Header
        pageTitle = new H1("Home");
        final Header header = new Header(new DrawerToggle(), pageTitle);
        addToNavbar(header);
    }

    private RouterLink[] getRouterLinks() {
        return new RouterLink[] { homeView, fetchCountryByNameView, fetchAllCountryView };
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        for (final RouterLink routerLink : getRouterLinks()) {
            if (routerLink.getHighlightCondition().shouldHighlight(routerLink, event)) {
                pageTitle.setText(routerLink.getText());
            }
        }
    }
}