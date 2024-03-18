package me.lofro.game.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Flags;
import co.aikar.commands.annotation.Subcommand;
import me.lofro.TankWarMinigame;
import me.lofro.utils.text.HexFormatter;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Subcommand("tankStaff")
@CommandPermission("admin.perm")
public class GameCommand extends BaseCommand {

    private final TankWarMinigame main;

    public GameCommand(final TankWarMinigame main) {
        this.main = main;
    }

    @Subcommand("setFirstCorner")
    @CommandCompletion("@location")
    private void setFirstCorner(CommandSender sender, Location corner) {
        var gameManager = main.getGameManager();
        if (gameManager.isRunning()) {
            sender.sendMessage(HexFormatter.hexFormatWithPrefix("&cEl juego ya está iniciado. Desactívalo para modificar esta información."));
        } else {
            gameManager.getGameData().setFirstCorner(corner);
            sender.sendMessage(HexFormatter.hexFormatWithPrefix("&aSe ha modificado la primera esquina del recinto del juego por la ubicación introducida."));
        }
    }

    @Subcommand("setSecondCorner")
    @CommandCompletion("@location")
    private void setSecondCorner(CommandSender sender, Location corner) {
        var gameManager = main.getGameManager();
        if (gameManager.isRunning()) {
            sender.sendMessage(HexFormatter.hexFormatWithPrefix("&cEl juego ya está iniciado. Desactívalo para modificar esta información."));
        } else {
            gameManager.getGameData().setSecondCorner(corner);
            sender.sendMessage(HexFormatter.hexFormatWithPrefix("&aSe ha modificado la segunda esquina del recinto del juego por la ubicación introducida."));
        }
    }

    @Subcommand("addParticipant")
    private void addPlayer(CommandSender sender, @Flags("other") Player player) {
        var gameManager = main.getGameManager();

        if (gameManager.isRunning()) {
            sender.sendMessage(HexFormatter.hexFormatWithPrefix("&cEl juego ya está iniciado. Desactívalo para modificar esta información."));
        } else {
            var gameData = gameManager.getGameData();
            if (gameData.getParticipants().contains(player.getUniqueId())) {
                sender.sendMessage(HexFormatter.hexFormatWithPrefix("&cEl jugador que intentas añadir como participante ya se encuentra en la lista de participantes."));
            } else {
                gameData.getParticipants().add(player.getUniqueId());
                sender.sendMessage(HexFormatter.hexFormatWithPrefix("&aHas añadido al jugador " + player.getName() + "&a a la lista de participantes."));
            }
        }
    }

    @Subcommand("removeParticipant")
    private void removePlayer(CommandSender sender, @Flags("other") Player player) {
        var gameManager = main.getGameManager();

        if (gameManager.isRunning()) {
            sender.sendMessage(HexFormatter.hexFormatWithPrefix("&cEl juego ya está iniciado. Desactívalo para modificar esta información."));
        } else {
            var gameData = gameManager.getGameData();
            if (!gameData.getParticipants().contains(player.getUniqueId())) {
                sender.sendMessage(HexFormatter.hexFormatWithPrefix("&cEl jugador que intentas eliminar no se encuentra en la lista de participantes."));
            } else {
                gameData.getParticipants().add(player.getUniqueId());
                sender.sendMessage(HexFormatter.hexFormatWithPrefix("&aHas eliminado al jugador " + player.getName() + "&a de la lista de participantes."));
            }
        }
    }

    @Subcommand("startGame")
    private void startGame(CommandSender sender) {
        var gameManager = main.getGameManager();

        if (gameManager.isRunning()) {
            sender.sendMessage(HexFormatter.hexFormatWithPrefix("&cEl juego ya está iniciado."));
        } else {
            sender.sendMessage(HexFormatter.hexFormatWithPrefix("&aSe ha iniciado el juego."));
            gameManager.startGame();
        }
    }

    @Subcommand("stopGame")
    private void stopGame(CommandSender sender) {
        var gameManager = main.getGameManager();

        if (!gameManager.isRunning()) {
            sender.sendMessage(HexFormatter.hexFormatWithPrefix("&cEl juego no está iniciado."));
        } else {
            sender.sendMessage(HexFormatter.hexFormatWithPrefix("&aSe ha detenido el juego."));
            gameManager.startGame();
        }
    }

}
