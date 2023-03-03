import java.io.File;

public class MidiSwingEx extends MidiSwing {

    public MidiSwingEx(int index, File file, int midiFileType) {
        super(index, file, midiFileType);
    }

    @Override
    public boolean isEnabledChannel(int channel) {
        // Workaround for some issue
        if (channel < 0) return false;
        return this.midiWindow.channelButton[channel].isSelected();
    }

    public void setupGUI() {
        this.midiWindow = new MidiWindowEx(this, "");
        this.updateChannels();
    }

    @Override
    public void checkForUpdates(final boolean b) {
        // Ignore check for update
    }

    public void notifyTranslation() {
        // Ignore message about translations
    }
}
