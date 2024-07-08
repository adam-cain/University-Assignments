package core;

import java.util.ArrayList;
import java.util.List;

import factory.UFO;
import gameObjects.Player;
import gameObjects.AlienObjects.Alien;
import gameObjects.BunkerObjects.Bunker;
import handler.CollisionHandler;
import handler.Handler;
import handler.InputHandler;
import handler.SoundManager;
import handler.GameEvents.GameEvent;
import interfaces.EventHandler;
import interfaces.Projectile;


public class Game {
    private static Game instance;
    private Player player;
    private AlienSwarm alienSwarm;
    private List<Projectile> projectiles;
    private Scoreboard scoreboard;
    private boolean isGameOver;
    private int currentLevel;
    private UFO ufo; // Assuming there's a UFO class.
    private List<Bunker> bunkers;

    private CollisionHandler collisionHandler;

    private Game() {
        this.player = new Player();
        this.currentLevel = 1;
        this.alienSwarm = new AlienSwarm(currentLevel);
        this.projectiles = new ArrayList<>();
        this.scoreboard = new Scoreboard();
        this.collisionHandler = CollisionHandler.getInstance();
        this.isGameOver = false;

        EventHandler inputHandler = new InputHandler(collisionHandler);
        EventHandler collisionHandler = new CollisionHandler(soundManager);
        EventHandler soundManager = new SoundManager();
    }

    public void fireEvent(GameEvent event) {
        chain.handleRequest(event);
    }

    // Public static method to get the instance
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    public void startGame() {
        // Initialize or reset game components
        player.reset();
        alienSwarm.reset();
        projectiles.clear();
        scoreboard.reset();
        isGameOver = false;
        currentLevel = 1;

        // Start the main game loop
        mainGameLoop();
    }

    private void mainGameLoop() {
        while (!isGameOver) {
            // Game loop operations
            updateGameObjects();
            checkCollisions();
            handlePlayerInput(); // This would be linked to actual player input in the game framework
            renderDisplay();
            checkEndOfLevel();
            checkGameOver();
            // Add a sleep or delay based on your game framework to control the loop timing
        }
        endGame();
    }

    private void renderDisplay() {
        // This method should be tied to the actual rendering code in your game
        // framework
        // Update the display with the current state of all game objects
    }

    private void checkEndOfLevel() {
        if (alienSwarm.allAliensDefeated()) {
            currentLevel++;
            startNextLevel();
        }
    }

    private void checkGameOver() {
        if (player.getLives() <= 0 || alienSwarm.hasReachedBase()) {
            isGameOver = true;
        }
    }

    private void startNextLevel() {
        // Prepare the game for the next level
        alienSwarm.reset();
        shields.forEach(Shield::repair);
        // More level initialization as needed
    }

    private void endGame() {
        // Display game over message and handle any cleanup
    }

    private void updateGameObjects() {
        // Update the player, aliens, and projectiles
        player.updatePlayer(); // Assuming there's an updatePlayer() method in the Player class
        alienSwarm.updateAliens(); // Assuming there's an updateAliens() method in the AlienSwarm class
        projectiles.removeIf(projectile -> projectile.isOutOfBounds());
    }

    private void checkCollisions() {
        // Handle collisions
        CollisionDetector.detectAndHandleCollisions(player, alienSwarm, shields, projectiles, scoreboard);
    }

    private void handlePlayerInput() {
        // This method should be connected to actual input handling in your game
        // framework
        // For example: if the left arrow key is pressed, then player.moveLeft();
    }
}