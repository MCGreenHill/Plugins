package net.project.api;

import net.project.api.entities.PlayerEntity;
import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Locale;

public class ProjectPlayer {

    private Player player;

    public ProjectPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PlayerEntity getEntity() {
        Session session = ProjectPlugin.getSessionFactory().openSession();
        Query query = session.createQuery(String.format("FROM Player WHERE uuid = '%s'", this.player.getUniqueId()));
        List<PlayerEntity> playerEntities = query.getResultList();
        PlayerEntity playerEntity;
        if (playerEntities.size() > 0) {
            playerEntity = playerEntities.get(0);
            session.close();
            return playerEntity;
        }
        playerEntity = new PlayerEntity();
        playerEntity.setUuid(this.player.getUniqueId().toString());
        playerEntity.setLocale(this.player.locale());
        session.beginTransaction();
        session.persist(playerEntity);
        session.getTransaction().commit();
        session.close();
        return playerEntity;
    }

    public Locale getProjectLocale() {
        return this.getEntity().getLocale();
    }

    public void setProjectLocale(Locale locale) {
        Session session = ProjectPlugin.getSessionFactory().openSession();
        PlayerEntity playerEntity = this.getEntity();
        playerEntity.setLocale(locale);
        session.beginTransaction();
        session.saveOrUpdate(playerEntity);
        session.getTransaction().commit();
        session.close();
    }

}
