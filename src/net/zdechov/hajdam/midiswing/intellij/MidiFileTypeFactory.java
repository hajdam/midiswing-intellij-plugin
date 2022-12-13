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

import com.intellij.icons.AllIcons;
import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory;

import javax.annotation.Nonnull;

/**
 * File template group descriptor factory.
 */
public class MidiFileTypeFactory implements FileTemplateGroupDescriptorFactory {

    @Nonnull
    @Override
    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
        final FileTemplateGroupDescriptor descriptor = new FileTemplateGroupDescriptor("MIDI File", AllIcons.FileTypes.Any_type);
        descriptor.addTemplate(new FileTemplateDescriptor("Empty MIDI file.mid", AllIcons.FileTypes.Any_type));
        return descriptor;
    }
}