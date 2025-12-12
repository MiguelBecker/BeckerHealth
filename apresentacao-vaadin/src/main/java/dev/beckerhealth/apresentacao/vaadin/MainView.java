package dev.beckerhealth.apresentacao.vaadin;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;

@Route("")
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

        RouterLink prontuariosLink = new RouterLink("Prontuários", ProntuariosView.class);

        RouterLink notificacoesLink = new RouterLink("Notificações", NotificacoesView.class);

        RouterLink relatoriosLink = new RouterLink("Relatórios", RelatoriosView.class);

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

