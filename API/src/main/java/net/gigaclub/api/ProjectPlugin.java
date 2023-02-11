package net.gigaclub.api;

import net.gigaclub.api.entities.PlayerEntity;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class ProjectPlugin extends JavaPlugin {

    private static ProjectPlugin plugin;
    private static SessionFactory sessionFactory;
    private static YamlConfiguration projectConfig;
    private static StandardServiceRegistryBuilder serviceRegistryBuilder;
    private static Configuration hibernateConfiguration;

    public static void generateSessionFactory() {
        try {
            ProjectPlugin.sessionFactory = hibernateConfiguration.buildSessionFactory(serviceRegistryBuilder.build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static YamlConfiguration getProjectConfig() {
        return projectConfig;
    }

    public static ProjectPlugin getPlugin() {
        return plugin;
    }

    public static void setPlugin(ProjectPlugin plugin) {
        ProjectPlugin.plugin = plugin;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static StandardServiceRegistryBuilder getServiceRegistryBuilder() {
        return serviceRegistryBuilder;
    }

    public static ResourceBundle getMessagesBundle(Locale locale) {
        try {
            return ResourceBundle.getBundle("messages", locale);
        } catch (MissingResourceException e) {
            return ResourceBundle.getBundle("messages", Locale.GERMAN);
        }
    }

    public static void loadConfig() {
        File file = new File("plugins//API", "config.yml");
        projectConfig = YamlConfiguration.loadConfiguration(file);

        projectConfig.addDefault("postgresql.url", "jdbc:postgresql://localhost:5432/test");
        projectConfig.addDefault("postgresql.username", "postgres");
        projectConfig.addDefault("postgresql.password", "test");

        projectConfig.options().copyDefaults(true);
        try {
            projectConfig.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setHibernateConfig() {
        hibernateConfiguration = new Configuration();
        hibernateConfiguration.setProperty("hibernate.connection.driver_url", "org.postgresql.Driver");
        hibernateConfiguration.setProperty("hibernate.connection.url", projectConfig.getString("postgresql.url"));
        hibernateConfiguration.setProperty("hibernate.connection.username", projectConfig.getString("postgresql.username"));
        hibernateConfiguration.setProperty("hibernate.connection.password", projectConfig.getString("postgresql.password"));
        hibernateConfiguration.setProperty("hibernate.show_sql", "true");
        hibernateConfiguration.setProperty("hibernate.hbm2ddl.auto", "update");

        hibernateConfiguration.addAnnotatedClass(PlayerEntity.class);
    }

    public static Configuration getHibernateConfiguration() {
        return hibernateConfiguration;
    }

    public static void setServiceRegistryBuilder() {
        serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(hibernateConfiguration.getProperties());
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        setPlugin(this);
        loadConfig();
        setHibernateConfig();
        setServiceRegistryBuilder();
        generateSessionFactory();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
