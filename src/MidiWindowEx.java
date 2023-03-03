import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MidiWindowEx extends MidiWindow {

    public MidiWindowEx(MidiSwing midiSwing, String s) {
        super(midiSwing, s);
    }

    @Override
    public void setVisible(boolean b) {
        // ignore
    }

    public void about() {
        URL uRL = this.getClass().getClassLoader().getResource("net/zdechov/hajdam/midiswing/intellij/resources/about.html");
        try {
            new AboutDialogFrame(uRL, 450, 262, this);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Error");
        }
    }

    public class AboutDialogFrame extends JFrame {

        public AboutDialogFrame(URL uRL, int n, int n2, MidiWindow midiWindow2) {
            super("MidiSwing");
            try {
                JEditorPane html = new JEditorPane(uRL);
                html.setEditable(false);
                html.setContentType("text/html");
                html.addHyperlinkListener(new HyperlinkListener() {
                    public void hyperlinkUpdate(HyperlinkEvent hyperlinkEvent) {
                        if (hyperlinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                            if (hyperlinkEvent instanceof HTMLFrameHyperlinkEvent) {
                                ((HTMLDocument)html.getDocument()).processHTMLFrameHyperlinkEvent((HTMLFrameHyperlinkEvent)hyperlinkEvent);
                            } else {
                                MidiWindowEx.openDesktopURL(hyperlinkEvent.getURL());
                            }
                        }
                    }
                });
                JScrollPane jScrollPane = new JScrollPane();
                JViewport jViewport = jScrollPane.getViewport();
                jViewport.add(html);
                this.getContentPane().add((Component)jScrollPane, "Center");
                this.setDefaultCloseOperation(2);
                this.setFocusable(false);
                this.setSize(new Dimension(n, n2));
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                this.setLocation(dimension.width / 2 - n / 2, dimension.height / 2 - n2 / 2);
                this.show();
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(midiWindow2, MidiSwing.resource.getString("CONNECTION_ERROR"));
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
