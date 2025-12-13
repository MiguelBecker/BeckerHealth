package dev.beckerhealth.apresentacao.vaadin.medico;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import dev.beckerhealth.aplicacao.notificacao.NotificacaoServicoAplicacao;
import dev.beckerhealth.aplicacao.notificacao.dto.NotificacaoResumo;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotificacaoComponent extends Div {
    private final NotificacaoServicoAplicacao notificacaoServico;
    private final Long usuarioId;
    private Div badge;
    private Dialog dialog;
    
    public NotificacaoComponent(NotificacaoServicoAplicacao notificacaoServico, Long usuarioId) {
        this.notificacaoServico = notificacaoServico;
        this.usuarioId = usuarioId;
        
        getStyle().set("position", "relative");
        getStyle().set("display", "inline-block");
        
        criarIcone();
        criarDialog();
        atualizarBadge();
    }
    
    private void criarIcone() {
        Div iconContainer = new Div();
        iconContainer.getStyle().set("position", "relative");
        iconContainer.getStyle().set("cursor", "pointer");
        iconContainer.getStyle().set("padding", "8px");
        iconContainer.getStyle().set("display", "flex");
        iconContainer.getStyle().set("align-items", "center");
        iconContainer.getStyle().set("justify-content", "center");
        
        String svgContent = "<svg width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" " +
                "style=\"stroke: #6B7280; stroke-width: 2; stroke-linecap: round; stroke-linejoin: round;\">" +
                "<path d=\"M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9\"></path>" +
                "<path d=\"M13.73 21a2 2 0 0 1-3.46 0\"></path>" +
                "</svg>";
        
        iconContainer.getElement().setProperty("innerHTML", svgContent);
        
        iconContainer.addClickListener(e -> {
            atualizarDialog();
            dialog.open();
        });
        
        add(iconContainer);
    }
    
    private void criarDialog() {
        dialog = new Dialog();
        dialog.setHeaderTitle("Notificações");
        dialog.setWidth("400px");
        dialog.setMaxWidth("90vw");
        
        atualizarDialog();
    }
    
    private void atualizarDialog() {
        dialog.removeAll();
        
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.setWidthFull();
        layout.setMaxHeight("500px");
        layout.getStyle().set("overflow-y", "auto");
        
        try {
            List<NotificacaoResumo> notificacoes = notificacaoServico.buscarNotificacoesPorUsuario(usuarioId);
            
            if (notificacoes == null || notificacoes.isEmpty()) {
                Div empty = new Div();
                empty.setText("Nenhuma notificação");
                empty.getStyle().set("padding", "40px");
                empty.getStyle().set("text-align", "center");
                empty.getStyle().set("color", "#6B7280");
                layout.add(empty);
            } else {
                // Ordenar por data (mais recentes primeiro) e limitar a 10
                notificacoes.stream()
                        .sorted((n1, n2) -> {
                            if (n1.getDataEnvio() == null || n2.getDataEnvio() == null) return 0;
                            return n2.getDataEnvio().compareTo(n1.getDataEnvio());
                        })
                        .limit(10)
                        .forEach(notificacao -> layout.add(criarCardNotificacao(notificacao)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Div error = new Div();
            error.setText("Erro ao carregar notificações: " + e.getMessage());
            error.getStyle().set("padding", "20px");
            error.getStyle().set("color", "#EF4444");
            layout.add(error);
        }
        
        dialog.add(layout);
    }
    
    private Div criarCardNotificacao(NotificacaoResumo notificacao) {
        Div card = new Div();
        card.getStyle().set("padding", "16px");
        card.getStyle().set("border-bottom", "1px solid #E5E7EB");
        card.getStyle().set("cursor", "pointer");
        
        if (notificacao.isNaoLida()) {
            card.getStyle().set("background", "#EFF6FF");
            card.getStyle().set("border-left", "4px solid #3B82F6");
        } else {
            card.getStyle().set("background", "white");
        }
        
        card.addClickListener(e -> {
            if (notificacao.isNaoLida() && notificacao.getId() != null) {
                try {
                    notificacaoServico.marcarComoLida(notificacao.getId());
                    atualizarBadge();
                    atualizarDialog();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (notificacao.getLink() != null && !notificacao.getLink().isEmpty()) {
                getUI().ifPresent(ui -> {
                    try {
                        ui.navigate(notificacao.getLink());
                        dialog.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }
        });
        
        H3 titulo = new H3(notificacao.getTitulo() != null ? notificacao.getTitulo() : "Sem título");
        titulo.getStyle().set("margin", "0 0 8px 0");
        titulo.getStyle().set("font-size", "14px");
        titulo.getStyle().set("font-weight", "600");
        titulo.getStyle().set("color", "#111827");
        
        Span mensagem = new Span(notificacao.getMensagem() != null ? notificacao.getMensagem() : "");
        mensagem.getStyle().set("display", "block");
        mensagem.getStyle().set("font-size", "13px");
        mensagem.getStyle().set("color", "#6B7280");
        mensagem.getStyle().set("margin-bottom", "8px");
        mensagem.getStyle().set("line-height", "1.5");
        
        Span data = new Span();
        if (notificacao.getDataEnvio() != null) {
            data.setText(notificacao.getDataEnvio().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            ));
        } else {
            data.setText("Data não disponível");
        }
        data.getStyle().set("font-size", "11px");
        data.getStyle().set("color", "#9CA3AF");
        
        card.add(titulo, mensagem, data);
        
        return card;
    }
    
    private void atualizarBadge() {
        if (badge != null) {
            remove(badge);
            badge = null;
        }
        
        try {
            int count = notificacaoServico.contarNotificacoesNaoLidas(usuarioId);
            
            if (count > 0) {
                badge = new Div();
                badge.getStyle().set("position", "absolute");
                badge.getStyle().set("top", "4px");
                badge.getStyle().set("right", "4px");
                badge.getStyle().set("width", "18px");
                badge.getStyle().set("height", "18px");
                badge.getStyle().set("background", "#EF4444");
                badge.getStyle().set("border-radius", "50%");
                badge.getStyle().set("display", "flex");
                badge.getStyle().set("align-items", "center");
                badge.getStyle().set("justify-content", "center");
                badge.getStyle().set("color", "white");
                badge.getStyle().set("font-size", "10px");
                badge.getStyle().set("font-weight", "600");
                badge.getStyle().set("z-index", "1000");
                badge.setText(String.valueOf(count > 9 ? "9+" : count));
                add(badge);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void atualizar() {
        atualizarBadge();
    }
}
