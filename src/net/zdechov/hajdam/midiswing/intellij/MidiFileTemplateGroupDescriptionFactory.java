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
package net.zdechov.hajdam.midiswing.intellij;

import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory;
import com.intellij.openapi.project.DumbAware;
import com.intellij.ui.IconManager;

import javax.annotation.Nonnull;
import javax.swing.Icon;

/**
 * File template group descriptor factory.
 */
public class MidiFileTemplateGroupDescriptionFactory implements FileTemplateGroupDescriptorFactory, DumbAware {

    @Nonnull
    @Override
    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
        Icon icon = IconManager.getInstance().getIcon("icons/midi_file.svg", this.getClass()); // AllIcons.FileTypes.Any_type
        final FileTemplateGroupDescriptor descriptor = new FileTemplateGroupDescriptor("MIDI File", icon);
        descriptor.addTemplate(new FileTemplateDescriptor("Empty MIDI file.bin", icon));
        return descriptor;
    }
}