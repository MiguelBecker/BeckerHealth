package dev.beckerhealth.apresentacao.vaadin;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;

public class MainView extends AppLayout {

    public MainView() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("BeckerHealth");
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                logo
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink consultasLink = new RouterLink("Consultas", ConsultasView.class);
        consultasLink.setIcon(VaadinIcon.CALENDAR.create());

        RouterLink prontuariosLink = new RouterLink("Prontuários", ProntuariosView.class);
        prontuariosLink.setIcon(VaadinIcon.FILE_TEXT.create());

        RouterLink notificacoesLink = new RouterLink("Notificações", NotificacoesView.class);
        notificacoesLink.setIcon(VaadinIcon.BELL.create());

        RouterLink relatoriosLink = new RouterLink("Relatórios", RelatoriosView.class);
        relatoriosLink.setIcon(VaadinIcon.CHART_LINE.create());

        Tabs tabs = new Tabs(
                new Tab(consultasLink),
                new Tab(prontuariosLink),
                new Tab(notificacoesLink),
                new Tab(relatoriosLink)
        );

        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        addToDrawer(tabs);
    }
}

