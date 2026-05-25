package com.banking.ui;

import com.banking.model.User;
import com.banking.service.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private JTextField nameField, emailField, phoneField;
    private JTextArea addressField;
    private JPasswordField passField, confirmField;
    private JButton createBtn;

    public RegisterFrame() {
        setTitle("NexusBank – Open Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(900, 600));

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(Theme.BG);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Theme.SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER_COL, 1), new EmptyBorder(36, 44, 36, 44)));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL; gc.weightx = 1.0; gc.gridx = 0; gc.gridwidth = 2;

        gc.gridy = 0; gc.insets = new Insets(0, 0, 4, 0);
        JLabel title = Theme.titleLabel("Open an Account");
        title.setHorizontalAlignment(SwingConstants.CENTER); card.add(title, gc);

        gc.gridy++; gc.insets = new Insets(0, 0, 20, 0);
        JLabel sub = Theme.mutedLabel("Join NexusBank and manage your wealth securely.");
        sub.setHorizontalAlignment(SwingConstants.CENTER); card.add(sub, gc);

        gc.gridwidth = 1; gc.weightx = 0.5;

        // Row 1
        gc.gridy++; gc.gridx = 0; gc.insets = new Insets(4, 0, 4, 10);
        card.add(Theme.bodyLabel("Full Name *"), gc);
        gc.gridx = 1; gc.insets = new Insets(4, 10, 4, 0);
        card.add(Theme.bodyLabel("Email Address *"), gc);

        gc.gridy++; gc.gridx = 0; gc.insets = new Insets(0, 0, 10, 10);
        nameField = Theme.textField(); nameField.setPreferredSize(new Dimension(260, 40)); card.add(nameField, gc);
        gc.gridx = 1; gc.insets = new Insets(0, 10, 10, 0);
        emailField = Theme.textField(); emailField.setPreferredSize(new Dimension(260, 40)); card.add(emailField, gc);

        // Row 2
        gc.gridy++; gc.gridx = 0; gc.insets = new Insets(4, 0, 4, 10);
        card.add(Theme.bodyLabel("Phone Number"), gc);
        gc.gridx = 1; gc.insets = new Insets(4, 10, 4, 0);
        card.add(Theme.bodyLabel("Password *"), gc);

        gc.gridy++; gc.gridx = 0; gc.insets = new Insets(0, 0, 10, 10);
        phoneField = Theme.textField(); phoneField.setPreferredSize(new Dimension(260, 40)); card.add(phoneField, gc);
        gc.gridx = 1; gc.insets = new Insets(0, 10, 10, 0);
        passField = Theme.passwordField(); passField.setPreferredSize(new Dimension(260, 40)); card.add(passField, gc);

        // Row 3
        gc.gridy++; gc.gridx = 0; gc.insets = new Insets(4, 0, 4, 10);
        card.add(Theme.bodyLabel("Address"), gc);
        gc.gridx = 1; gc.insets = new Insets(4, 10, 4, 0);
        card.add(Theme.bodyLabel("Confirm Password *"), gc);

        gc.gridy++; gc.gridx = 0; gc.insets = new Insets(0, 0, 10, 10);
        addressField = Theme.textArea(); addressField.setRows(3);
        JScrollPane asp = new JScrollPane(addressField);
        asp.setBorder(BorderFactory.createLineBorder(Theme.BORDER_COL, 1));
        asp.setPreferredSize(new Dimension(260, 72)); card.add(asp, gc);
        gc.gridx = 1; gc.insets = new Insets(0, 10, 10, 0);
        confirmField = Theme.passwordField(); confirmField.setPreferredSize(new Dimension(260, 40)); card.add(confirmField, gc);

        // Buttons
        gc.gridy++; gc.gridx = 0; gc.gridwidth = 2; gc.insets = new Insets(16, 0, 8, 0);
        createBtn = Theme.primaryButton("Create Account"); createBtn.setPreferredSize(new Dimension(560, 44)); card.add(createBtn, gc);

        gc.gridy++; gc.insets = new Insets(4, 0, 0, 0);
        JPanel backRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0)); backRow.setOpaque(false);
        backRow.add(Theme.mutedLabel("Already have an account?"));
        JButton backBtn = new JButton("Sign In");
        backBtn.setFont(Theme.FONT_BODY); backBtn.setForeground(Theme.PRIMARY);
        backBtn.setContentAreaFilled(false); backBtn.setBorderPainted(false); backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backRow.add(backBtn); card.add(backRow, gc);

        GridBagConstraints rc = new GridBagConstraints(); rc.gridx = 0; rc.gridy = 0;
        root.add(card, rc);
        setContentPane(root);

        createBtn.addActionListener(e -> doRegister());
        backBtn.addActionListener(e -> { dispose(); new LoginFrame(); });
        setVisible(true);
    }

    private void doRegister() {
        String name = nameField.getText().trim(), email = emailField.getText().trim();
        String phone = phoneField.getText().trim(), address = addressField.getText().trim();
        String pass = new String(passField.getPassword()), confirm = new String(confirmField.getPassword());
        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) { Theme.showError(this, "Name, email and password are required."); return; }
        if (!pass.equals(confirm)) { Theme.showError(this, "Passwords do not match."); return; }
        if (pass.length() < 6) { Theme.showError(this, "Password must be at least 6 characters."); return; }

        createBtn.setEnabled(false); createBtn.setText("Creating...");
        SwingWorker<User, Void> w = new SwingWorker<>() {
            @Override protected User doInBackground() throws Exception { return AuthService.register(name, email, phone, address, pass); }
            @Override protected void done() {
                createBtn.setEnabled(true); createBtn.setText("Create Account");
                try {
                    User user = get();
                    Theme.showSuccess(RegisterFrame.this, "Account created! Welcome to NexusBank.");
                    dispose(); new MainFrame(user);
                } catch (Exception ex) { Theme.showError(RegisterFrame.this, ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage()); }
            }
        };
        w.execute();
    }
}
