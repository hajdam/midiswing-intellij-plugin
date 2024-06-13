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
import java.io.File;

/**
 * Overwrite MidiSwing from default package.
 */
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
