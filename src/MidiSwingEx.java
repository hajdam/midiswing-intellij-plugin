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
}
