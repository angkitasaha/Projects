package com.banking.ui;

import com.banking.model.User;
import com.banking.service.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginBtn;

    public LoginFrame() {
        setTitle("NexusBank – Sign In");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(900, 600));

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Theme.BG);

        // Left branding panel
        JPanel left = new JPanel(new GridBagLayout());
        left.setBackground(new Color(0x0B1120));
        left.setPreferredSize(new Dimension(420, 600));
        GridBagConstraints lc = new GridBagConstraints();
        lc.gridx = 0; lc.insets = new Insets(12, 20, 12, 20); lc.anchor = GridBagConstraints.CENTER;

        lc.gridy = 0;
        JLabel bank = Theme.label("NexusBank", new Font("SansSerif", Font.BOLD, 40), Theme.PRIMARY);
        left.add(bank, lc);

        lc.gridy++;
        JLabel tagline = new JLabel("<html><div style='text-align:center'>Secure. Fast. Reliable.<br>Your bank, anytime.</div></html>");
        tagline.setFont(Theme.FONT_BODY); tagline.setForeground(Theme.TEXT_MUTED);
        left.add(tagline, lc);

        // Right login form
        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(Theme.BG);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Theme.SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER_COL, 1), new EmptyBorder(40, 44, 40, 44)));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL; gc.weightx = 1.0; gc.gridx = 0;
        gc.insets = new Insets(5, 0, 5, 0);

        gc.gridy = 0;
        JLabel title = Theme.titleLabel("Welcome Back");
        title.setHorizontalAlignment(SwingConstants.CENTER); card.add(title, gc);
        gc.gridy++;
        JLabel sub = Theme.mutedLabel("Sign in to your NexusBank account");
        sub.setHorizontalAlignment(SwingConstants.CENTER); card.add(sub, gc);

        gc.gridy++; gc.insets = new Insets(20, 0, 4, 0);
        card.add(Theme.bodyLabel("Email Address"), gc);
        gc.gridy++; gc.insets = new Insets(0, 0, 6, 0);
        emailField = Theme.textField(); emailField.setPreferredSize(new Dimension(360, 42)); card.add(emailField, gc);

        gc.gridy++; gc.insets = new Insets(6, 0, 4, 0);
        card.add(Theme.bodyLabel("Password"), gc);
        gc.gridy++; gc.insets = new Insets(0, 0, 20, 0);
        passwordField = Theme.passwordField(); passwordField.setPreferredSize(new Dimension(360, 42)); card.add(passwordField, gc);

        gc.gridy++; gc.insets = new Insets(0, 0, 10, 0);
        loginBtn = Theme.primaryButton("Sign In"); loginBtn.setPreferredSize(new Dimension(360, 44)); card.add(loginBtn, gc);

        gc.gridy++; gc.insets = new Insets(4, 0, 0, 0);
        JPanel regRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        regRow.setOpaque(false);
        regRow.add(Theme.mutedLabel("Don't have an account?"));
        JButton regBtn = new JButton("Open an account");
        regBtn.setFont(Theme.FONT_BODY); regBtn.setForeground(Theme.PRIMARY);
        regBtn.setContentAreaFilled(false); regBtn.setBorderPainted(false); regBtn.setFocusPainted(false);
        regBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        regRow.add(regBtn);
        card.add(regRow, gc);

        GridBagConstraints rc = new GridBagConstraints(); rc.gridx = 0; rc.gridy = 0; rc.fill = GridBagConstraints.NONE;
        right.add(card, rc);

        root.add(left, BorderLayout.WEST);
        root.add(right, BorderLayout.CENTER);
        setContentPane(root);

        loginBtn.addActionListener(e -> doLogin());
        regBtn.addActionListener(e -> { dispose(); new RegisterFrame(); });
        KeyAdapter enter = new KeyAdapter() { @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) doLogin(); }};
        emailField.addKeyListener(enter); passwordField.addKeyListener(enter);
        setVisible(true);
    }

    private void doLogin() {
        String email = emailField.getText().trim();
        String pass = new String(passwordField.getPassword());
        if (email.isEmpty() || pass.isEmpty()) { Theme.showError(this, "Please enter email and password."); return; }
        loginBtn.setEnabled(false); loginBtn.setText("Signing in...");
        SwingWorker<User, Void> w = new SwingWorker<>() {
            @Override protected User doInBackground() throws Exception { return AuthService.login(email, pass); }
            @Override protected void done() {
                loginBtn.setEnabled(true); loginBtn.setText("Sign In");
                try { User user = get(); dispose(); new MainFrame(user); }
                catch (Exception ex) { Theme.showError(LoginFrame.this, ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage()); }
            }
        };
        w.execute();
    }
}
