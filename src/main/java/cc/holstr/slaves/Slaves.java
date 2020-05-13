package cc.holstr.slaves;

import cc.holstr.slaves.model.PlayerQuests;
import cc.holstr.slaves.model.Quest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class Slaves extends JavaPlugin {

    private String slaveGroup;
    private String slaveheadGroup;

    private List<Quest> quests;
    private PlayerQuests playerQuests;

    @Override
    public void onEnable() {
        getLogger().info("Slaves has been enabled.");

        //get instance of luckperms interface
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LuckPerms api = provider.getProvider();
        }
        //create config if it doesn't exist
        File file = new File(getDataFolder() + File.separator + "config.yml");
        if(!file.exists()) {
            saveDefaultConfig();
        }
        //load config instance
        FileConfiguration config = getConfig();

        slaveGroup = config.getString("slave-group");
        slaveheadGroup = config.getString("slavehead-group");

        //load quests from folder
        File questFolder = new File(getDataFolder() + File.separator + "quests");
        //create quest folder if it doesn't exist
        if(!questFolder.exists()) {
            try {
                questFolder.mkdir();
                File exampleQuest = new File(getDataFolder() + File.separator + "quests" + File.separator + "quest.yml");
                exampleQuest.createNewFile();
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "unable to create quest folder. Plugin will not work.");
            }
        }
        //load quests from folder
        File[] questFiles = questFolder.listFiles();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        quests = new ArrayList<>();
        for(File f : questFiles) {
            try {
                Quest q = mapper.readValue(f, Quest.class);
                quests.add(q);
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "unable to load quest at " + f.getPath());
            }
        }

        //load playerquests
        File playerQuestsFile = new File(getDataFolder() + File.separator + "playerquests.yml");
        if(!playerQuestsFile.exists()) {
            try {
                playerQuestsFile.createNewFile();
                playerQuests = new PlayerQuests();
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "unable to create new player quests!");
            }
        } else {
            try {
                playerQuests = mapper.readValue(playerQuestsFile, PlayerQuests.class);
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "unable to load player quests!");
            }
        }

        String defaultQuestName = config.getString("default-quest");
        Quest defaultQuest = null;
        for(Quest q : quests) {
            if(defaultQuestName.equals(q.getId())) {
                defaultQuest = q;
            }
        }

        //load listeners
        Bukkit.getPluginManager().registerEvents(new JoinEvent(playerQuests, quests, defaultQuest), this);
    }

    @Override
    public void onDisable() {
        saveConfig();

        //save quests
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        String stub = getDataFolder() + File.separator;
        for(Quest q : quests) {
            try {
                mapper.writeValue(new File(stub + q.getId() + ".yml"), q);
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "unable to save quest id: " + q.getId());
            }
        }

        //save playerquests
        File playerQuestsFile = new File(getDataFolder() + File.separator + "playerquests.yml");
        try {
            mapper.writeValue(playerQuestsFile, playerQuests);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "unable to save player quests!");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("quest")) {
            //execute quest logic
            return true;
        }
        return false;
    }
}
