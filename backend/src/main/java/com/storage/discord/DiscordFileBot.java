package com.storage.discord;

import com.storage.filehandler.FileHandler;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.utils.AttachmentProxy;
import net.dv8tion.jda.api.utils.FileUpload;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DiscordFileBot {
    private final JDA bot;
    private final Dotenv dotenv  = Dotenv.load(); // load .env file
    public DiscordFileBot() {
        //create discord bot with JDA (Java discord api)
        this.bot = JDABuilder.createDefault(this.dotenv.get("discord_token"))
                .setActivity(Activity.playing("Storing"))
                .build();
    }
    public List<Long> sendFile(MultipartFile file) throws Exception {
        //get splitted files
        ArrayList<FileUpload> files = FileHandler.splitFile(file, file.getOriginalFilename());
        List<Long> ids = new ArrayList<>(); //discord message ids list to store in db and later retrieve them
        for(FileUpload fileUpload : files) {
            //send file to discord private server
            Message message = this.bot.getTextChannelById(this.dotenv.get("channel_id")).sendFiles(fileUpload).complete();
            //get the message id and add it to the list
            Long messageId = message.getIdLong();
            ids.add(messageId);
        }
        return ids;
    }
    public File getFile(String fileName, Long messageId) {
        File file = new File(fileName); //create new file
        //retrieve the message with the id
        Message message = this.bot.getTextChannelById(this.dotenv.get("channel_id")).retrieveMessageById(messageId).complete();
        //get the message attachment and download it to the file object
        List<Message.Attachment> attachments = message.getAttachments();
        AttachmentProxy proxy = attachments.get(0).getProxy();
        proxy.downloadToFile(file);
        return file;
    }
}
