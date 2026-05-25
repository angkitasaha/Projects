package com.banking.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Theme {
    public static final Color BG         = new Color(0x0F172A);
    public static final Color SURFACE    = new Color(0x1E293B);
    public static final Color SURFACE2   = new Color(0x263348);
    public static final Color BORDER_COL = new Color(0x334155);
    public static final Color PRIMARY    = new Color(0x3B82F6);
    public static final Color PRIMARY_DK = new Color(0x2563EB);
    public static final Color SUCCESS    = new Color(0x22C55E);
    public static final Color DANGER     = new Color(0xEF4444);
    public static final Color WARNING    = new Color(0xF59E0B);
    public static final Color TEXT       = new Color(0xF8FAFC);
    public static final Color TEXT_MUTED = new Color(0x94A3B8);
    public static final Color INPUT_BG   = new Color(0x1E293B);

    public static final Font FONT_TITLE   = new Font("SansSerif", Font.BOLD, 26);
    public static final Font FONT_HEADING = new Font("SansSerif", Font.BOLD, 18);
    public static final Font FONT_SUBHEAD = new Font("SansSerif", Font.BOLD, 14);
    public static final Font FONT_BODY    = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_SMALL   = new Font("SansSerif", Font.PLAIN, 11);

    public static JLabel label(String text, Font font, Color color) {
        JLabel l = new JLabel(text); l.setFont(font); l.setForeground(color); l.setOpaque(false); return l;
    }
    public static JLabel titleLabel(String t)   { return label(t, FONT_TITLE,   TEXT); }
    public static JLabel headingLabel(String t) { return label(t, FONT_HEADING, TEXT); }
    public static JLabel subLabel(String t)     { return label(t, FONT_SUBHEAD, TEXT); }
    public static JLabel bodyLabel(String t)    { return label(t, FONT_BODY,    TEXT); }
    public static JLabel mutedLabel(String t)   { return label(t, FONT_BODY, TEXT_MUTED); }

    public static JTextField textField() {
        JTextField f = new JTextField();
        f.setFont(FONT_BODY); f.setForeground(TEXT); f.setBackground(INPUT_BG); f.setCaretColor(TEXT);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COL, 1), new EmptyBorder(8, 12, 8, 12)));
        return f;
    }

    public static JPasswordField passwordField() {
        JPasswordField f = new JPasswordField();
        f.setFont(FONT_BODY); f.setForeground(TEXT); f.setBackground(INPUT_BG); f.setCaretColor(TEXT);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COL, 1), new EmptyBorder(8, 12, 8, 12)));
        return f;
    }

    public static JTextArea textArea() {
        JTextArea ta = new JTextArea();
        ta.setFont(FONT_BODY); ta.setForeground(TEXT); ta.setBackground(INPUT_BG); ta.setCaretColor(TEXT);
        ta.setLineWrap(true); ta.setWrapStyleWord(true); ta.setBorder(new EmptyBorder(8, 12, 8, 12));
        return ta;
    }

    private static JButton styledButton(String text, Color baseColor, Color hoverColor, Color pressColor) {
        JButton b = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? pressColor : (getModel().isRollover() ? hoverColor : baseColor));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(FONT_SUBHEAD); b.setForeground(Color.WHITE); b.setContentAreaFilled(false);
        b.setBorderPainted(false); b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(10, 20, 10, 20)); b.setOpaque(false);
        return b;
    }

    public static JButton primaryButton(String text) {
        return styledButton(text, PRIMARY, PRIMARY_DK, new Color(0x1D4ED8));
    }

    public static JButton dangerButton(String text) {
        return styledButton(text, DANGER, new Color(0xDC2626), new Color(0xB91C1C));
    }

    public static JButton outlineButton(String text) {
        JButton b = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) { g2.setColor(SURFACE2); g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10)); }
                g2.setColor(BORDER_COL); g2.draw(new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 10, 10));
                g2.dispose(); super.paintComponent(g);
            }
        };
        b.setFont(FONT_BODY); b.setForeground(TEXT); b.setContentAreaFilled(false); b.setBorderPainted(false);
        b.setFocusPainted(false); b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(8, 16, 8, 16)); b.setOpaque(false);
        return b;
    }

    public static JComboBox<String> comboBox(String... items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(FONT_BODY); cb.setForeground(TEXT); cb.setBackground(INPUT_BG);
        cb.setBorder(BorderFactory.createLineBorder(BORDER_COL, 1));
        cb.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean sel, boolean f) {
                super.getListCellRendererComponent(l, v, i, sel, f);
                setBackground(sel ? SURFACE2 : INPUT_BG); setForeground(TEXT); setBorder(new EmptyBorder(6,10,6,10));
                return this;
            }
        });
        return cb;
    }

    public static JPanel card() {
        JPanel p = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(SURFACE); g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.setColor(BORDER_COL); g2.draw(new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 12, 12));
                g2.dispose();
            }
        };
        p.setOpaque(false); p.setBorder(new EmptyBorder(24, 28, 24, 28));
        return p;
    }

    public static JPanel statCard(String title, String value, Color valueColor) {
        JPanel card = card(); card.setLayout(new GridLayout(2, 1, 0, 6));
        JLabel t = label(title.toUpperCase(), FONT_SMALL, TEXT_MUTED);
        JLabel v = label(value, new Font("SansSerif", Font.BOLD, 22), valueColor != null ? valueColor : TEXT);
        card.add(t); card.add(v);
        return card;
    }

    public static JScrollPane scrollPane(Component view) {
        JScrollPane sp = new JScrollPane(view);
        sp.setBackground(BG); sp.getViewport().setBackground(SURFACE);
        sp.setBorder(BorderFactory.createLineBorder(BORDER_COL, 1));
        sp.getVerticalScrollBar().setUI(new DarkScrollBarUI());
        sp.getHorizontalScrollBar().setUI(new DarkScrollBarUI());
        return sp;
    }

    public static JTable table(String[] cols) {
        JTable t = new JTable(new javax.swing.table.DefaultTableModel(new Object[][]{}, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        t.setFont(FONT_BODY); t.setForeground(TEXT); t.setBackground(SURFACE);
        t.setSelectionBackground(SURFACE2); t.setSelectionForeground(TEXT);
        t.setGridColor(BORDER_COL); t.setShowGrid(true); t.setRowHeight(36);
        t.getTableHeader().setFont(FONT_SUBHEAD); t.getTableHeader().setForeground(TEXT_MUTED);
        t.getTableHeader().setBackground(SURFACE); t.getTableHeader().setBorder(BorderFactory.createLineBorder(BORDER_COL,1));
        t.setIntercellSpacing(new Dimension(8, 4)); t.setFillsViewportHeight(true);
        return t;
    }

    public static JTabbedPane tabbedPane() {
        JTabbedPane tp = new JTabbedPane();
        tp.setFont(FONT_SUBHEAD); tp.setForeground(TEXT); tp.setBackground(BG);
        UIManager.put("TabbedPane.selected", SURFACE2); UIManager.put("TabbedPane.background", BG);
        UIManager.put("TabbedPane.foreground", TEXT); UIManager.put("TabbedPane.tabAreaBackground", BG);
        UIManager.put("TabbedPane.contentAreaColor", BG);
        return tp;
    }

    public static void showError(Component p, String msg) {
        JOptionPane.showMessageDialog(p, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public static void showSuccess(Component p, String msg) {
        JOptionPane.showMessageDialog(p, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    public static String formatMoney(double amount) { return String.format("$%,.2f", amount); }

    public static class DarkScrollBarUI extends BasicScrollBarUI {
        @Override protected void configureScrollBarColors() { thumbColor = SURFACE2; trackColor = BG; }
        @Override protected JButton createDecreaseButton(int o) { JButton b = new JButton(); b.setPreferredSize(new Dimension(0,0)); return b; }
        @Override protected JButton createIncreaseButton(int o) { JButton b = new JButton(); b.setPreferredSize(new Dimension(0,0)); return b; }
    }
}
