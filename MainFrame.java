package com.banking.ui;

import com.banking.model.User;
import com.banking.ui.admin.AdminPanel;
import com.banking.ui.customer.CustomerPanel;
import com.banking.ui.staff.StaffPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {
    private final User user;

    public MainFrame(User user) {
        this.user = user;
        setTitle("NexusBank – " + roleTitle(user.getRole()));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1024, 680));

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Theme.BG);

        // Top navigation bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Theme.SURFACE);
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER_COL),
                new EmptyBorder(12, 28, 12, 28)));

        JLabel logo = Theme.label("NexusBank", new Font("SansSerif", Font.BOLD, 22), Theme.PRIMARY);

        JPanel rightNav = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 0));
        rightNav.setOpaque(false);

        JLabel chip = new JLabel("  " + user.getRole().toUpperCase() + "  ");
        chip.setFont(Theme.FONT_SMALL); chip.setForeground(Theme.PRIMARY);
        chip.setBackground(new Color(0x1E3A5F)); chip.setOpaque(true);
        chip.setBorder(new EmptyBorder(4, 10, 4, 10));

        JButton logout = Theme.outlineButton("Logout");
        logout.setFont(Theme.FONT_SMALL);
        logout.addActionListener(e -> doLogout());

        rightNav.add(chip); rightNav.add(Theme.bodyLabel(user.getName())); rightNav.add(logout);
        topBar.add(logo, BorderLayout.WEST); topBar.add(rightNav, BorderLayout.EAST);

        JPanel content;
        switch (user.getRole()) {
            case "admin": content = new AdminPanel(user, this); break;
            case "staff": content = new StaffPanel(user, this); break;
            default: content = new CustomerPanel(user, this); break;
        }

        root.add(topBar, BorderLayout.NORTH); root.add(content, BorderLayout.CENTER);
        setContentPane(root);
        setVisible(true);
    }

    public void doLogout() {
        int c = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (c == JOptionPane.YES_OPTION) { dispose(); new LoginFrame(); }
    }

    private String roleTitle(String role) {
        switch (role) {
            case "admin": return "Admin Panel";
            case "staff": return "Staff Panel";
            default: return "Customer Portal";
        }
    }
}
