/*
 * Copyright (C) ExBin Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.intellij.ui.components.JBScrollPane;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Overwrite MidiWindow from default package.
 */
public class MidiWindowEx extends MidiWindow {

    public MidiWindowEx(MidiSwing midiSwing, String s) {
        super(midiSwing, s);
    }

    @Override
    public void setVisible(boolean b) {
        // ignore
    }

    public void about() {
        new AboutDialogFrame("/net/zdechov/hajdam/midiswing/intellij/resources/about.html", 450, 262, this);
    }

    private static class AboutDialogFrame extends JFrame {

        public AboutDialogFrame(String resourcePath, int windowWidth, int windowHeight, MidiWindow midiWindow) {
            super("MidiSwing");
            try {
                JEditorPane html = new JEditorPane();
                html.setEditable(false);
                html.setContentType("text/html");
                html.setPage(this.getClass().getResource(resourcePath));
                html.addHyperlinkListener(hyperlinkEvent -> {
                    if (hyperlinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        if (hyperlinkEvent instanceof HTMLFrameHyperlinkEvent) {
                            ((HTMLDocument) html.getDocument()).processHTMLFrameHyperlinkEvent((HTMLFrameHyperlinkEvent) hyperlinkEvent);
                        } else {
                            MidiWindowEx.openDesktopURL(hyperlinkEvent.getURL());
                        }
                    }
                });
                JBScrollPane scrollPane = new JBScrollPane();
                scrollPane.getViewport().setView(html);
                this.getContentPane().add(scrollPane, BorderLayout.CENTER);
                this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                this.setFocusable(false);
                this.setSize(new Dimension(windowWidth, windowHeight));
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                this.setLocation(dimension.width / 2 - windowWidth / 2, dimension.height / 2 - windowHeight / 2);
                this.setVisible(true);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(midiWindow, MidiSwing.resource.getString("CONNECTION_ERROR"));
            }
        }
    }

    public static void openDesktopURL(final URL url) {
        SwingUtilities.invokeLater(() -> {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        java.net.URI uri = url.toURI();
                        desktop.browse(uri);
                        return;
                    } catch (IOException | URISyntaxException ex) {
                        Logger.getLogger(MidiWindowEx.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            BrowserControl.displayURL(url);
        });
    }
}
