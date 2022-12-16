package net.zdechov.hajdam.midiswing.intellij;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

public class MidiSwingWrapper {

    private final Class<?> midiSwingClass;
    private Object midiSwingInst = null;

    private JFrame midiWindow = null;
    private JComponent component = null;
    private JMenuBar menuBar = null;

    public MidiSwingWrapper() {
        try {
            midiSwingClass = Class.forName("MidiSwingEx");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void openForFile(@Nullable File file) {
        try {
            Constructor<?> midiSwingConstructor = midiSwingClass.getConstructor(int.class, File.class, int.class);
            Locale defaultLocale = Locale.getDefault();
            try {
                Locale.setDefault(Locale.ENGLISH);
                midiSwingInst = midiSwingConstructor.newInstance(0, file, 0);
            } finally {
                Locale.setDefault(defaultLocale);
            }
            Field midiWindowField = midiSwingInst.getClass().getSuperclass().getDeclaredField("midiWindow");
            midiWindow = (JFrame) midiWindowField.get(midiSwingInst);
            component = (JComponent) midiWindow.getContentPane();
            midiWindow.setContentPane(new JLabel(""));
            menuBar = midiWindow.getJMenuBar();
            midiWindow.setJMenuBar(null);
            midiWindow.setVisible(false);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
//        midiWindow.dispatchEvent(new WindowEvent(midiWindow, WindowEvent.WINDOW_CLOSING));
        midiWindow.dispose();
        try {
            Method stopMethod = midiSwingInst.getClass().getSuperclass().getMethod("stop");
            stopMethod.invoke(midiSwingInst);
//            Method finalizeMethod = midiSwingInst.getClass().getSuperclass().getMethod("finalize");
//            finalizeMethod.invoke(midiSwingInst);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Nonnull
    public JComponent getComponent() {
        return component;
    }

    @Nonnull
    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
