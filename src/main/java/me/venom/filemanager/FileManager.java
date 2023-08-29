package me.venom.filemanager;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public final class FileManager
{

    private final Plugin main;
    private final String fileName;

    private File customConfigFile;
    private FileConfiguration customConfig;

    public FileManager(Plugin plugin, String fileName)
    {
        main = plugin;
        this.fileName = fileName;
    }

    public FileConfiguration getFile() { return customConfig; }

    public void getResourceFile()
    {
        customConfigFile = new File(main.getDataFolder(), fileName + ".yml");
        if(!customConfigFile.exists())
        {
            customConfigFile.getParentFile().mkdirs();
            main.saveResource(fileName, false);
        }
        loadFile();
    }

    public void createConfigFile()
    {
        customConfigFile = new File(main.getDataFolder(), fileName + ".yml");
        if(customConfigFile.exists()) { loadFile(); }
        else
        {
            try
            {
                customConfigFile.getParentFile().mkdirs();
                if(customConfigFile.createNewFile())
                {
                    loadFile();
                }
            }
            catch(IOException e)
            {
                sendErrorMessage("File " + fileName + " could not be created!");
            }
        }
    }

    private void loadFile()
    {
        customConfig = new YamlConfiguration();
        try { customConfig.load(customConfigFile); }
        catch(IOException | InvalidConfigurationException e)
        {
            sendErrorMessage("File " + fileName + " could not be loaded!");
        }
    }

    public void saveFile()
    {
        try { customConfig.save(customConfigFile); }
        catch(IOException e)
        {
            sendErrorMessage("File " + fileName + " could not be saved!");
        }
    }

    public void sendErrorMessage(String... msgs)
    {
        for(String message : msgs)
        {
            main.getServer().getConsoleSender().sendMessage(message);
        }
    }

}
