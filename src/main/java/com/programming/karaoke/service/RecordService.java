package com.programming.karaoke.service;

import com.programming.karaoke.model.Recordings;
import com.programming.karaoke.repository.RecordingRepository;
import jdk.jfr.Recording;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.util.Optional;

@Service
public class RecordService {
    @Autowired
    private RecordingRepository recordingRepository;

    public Recordings createRecording(MultipartFile file) throws Exception {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file.getInputStream());
        AudioFormat format = audioInputStream.getFormat();
        long duration = (long) (audioInputStream.getFrameLength() / format.getFrameRate() * 1000);
        String name = file.getOriginalFilename();
        byte[] bytes = file.getBytes();
        Recordings recordings = new Recordings();
        recordings.setName(name);
        recordings.setFormat(String.valueOf(format));
        recordings.setDuration(duration);
        recordings.setBytes(bytes);
        recordingRepository.save(recordings);
        return  recordings;

    }

    public Optional<Recordings> getRecordingById(Long id) {
        return recordingRepository.findById(id);
    }

    public void deleteRecordingById( Long id) {
            recordingRepository.deleteById(id);

    }

    public byte[] getRecordingBytes(Recordings recordings) throws  Exception {
        return  recordings.getBytes();
    }

}
