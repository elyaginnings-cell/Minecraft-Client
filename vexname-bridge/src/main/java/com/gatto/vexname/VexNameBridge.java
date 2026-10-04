package com.gatto.vexname;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class VexNameBridge implements ModInitializer {
    private static final Pattern VALID_NAME = Pattern.compile("[A-Za-z0-9_-]{1,32}");
    private static final Map<UUID, String> NAMES = new HashMap<>();
    private static Path saveFile;

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("vexname")
                .then(literal("set")
                    .then(argument("name", StringArgumentType.word())
                        .executes(ctx -> setName(ctx.getSource(), StringArgumentType.getString(ctx, "name")))))
                .then(literal("get")
                    .executes(ctx -> getName(ctx.getSource())))
                .then(literal("clear")
                    .executes(ctx -> clearName(ctx.getSource())))
                .then(literal("spawn")
                    .then(argument("x", DoubleArgumentType.doubleArg())
                        .then(argument("y", DoubleArgumentType.doubleArg())
                            .then(argument("z", DoubleArgumentType.doubleArg())
                                .executes(ctx -> spawn(
                                    ctx.getSource(),
                                    DoubleArgumentType.getDouble(ctx, "x"),
                                    DoubleArgumentType.getDouble(ctx, "y"),
                                    DoubleArgumentType.getDouble(ctx, "z")
                                ))))));
        });
    }

    private static ServerPlayerEntity player(ServerCommandSource source) throws CommandSyntaxException {
        return source.getPlayerOrThrow();
    }

    private static int setName(ServerCommandSource source, String name) throws CommandSyntaxException {
        ServerPlayerEntity p = player(source);
        if (!VALID_NAME.matcher(name).matches()) {
            source.sendError(Text.literal("Name must be 1-32 characters: A-Z, a-z, 0-9, _ or -."));
            return 0;
        }
        ensureLoaded(source.getServer());
        NAMES.put(p.getUuid(), name);
        save();
        source.sendFeedback(() -> Text.literal("Active VexBot name set to: " + name), false);
        return 1;
    }

    private static int getName(ServerCommandSource source) throws CommandSyntaxException {
        ServerPlayerEntity p = player(source);
        ensureLoaded(source.getServer());
        String name = NAMES.get(p.getUuid());
        source.sendFeedback(() -> Text.literal(name == null
            ? "You do not have an active VexBot name."
            : "Active VexBot name: " + name), false);
        return name == null ? 0 : 1;
    }

    private static int clearName(ServerCommandSource source) throws CommandSyntaxException {
        ServerPlayerEntity p = player(source);
        ensureLoaded(source.getServer());
        NAMES.remove(p.getUuid());
        save();
        source.sendFeedback(() -> Text.literal("Active VexBot name cleared."), false);
        return 1;
    }

    private static int spawn(ServerCommandSource source, double x, double y, double z) throws CommandSyntaxException {
        ServerPlayerEntity p = player(source);
        ensureLoaded(source.getServer());
        String name = NAMES.get(p.getUuid());
        if (name == null) {
            source.sendError(Text.literal("Set your VexBot name first with /vexname set <name>."));
            return 0;
        }

        MinecraftServer server = source.getServer();
        String command = "vexbot spawn " + name + " " + Double.toString(x) + " " + Double.toString(y) + " " + Double.toString(z);
        int result = server.getCommandManager().executeWithPrefix(server.getCommandSource(), command);

        if (result > 0) {
            source.sendFeedback(() -> Text.literal("Spawned VexBot: " + name), false);
        }
        return result;
    }

    private static void ensureLoaded(MinecraftServer server) {
        if (saveFile != null) return;
        saveFile = server.getRunDirectory().resolve("config").resolve("vexname").resolve("names.properties");
        try {
            Files.createDirectories(saveFile.getParent());
            if (Files.exists(saveFile)) {
                Properties props = new Properties();
                try (var in = Files.newInputStream(saveFile)) {
                    props.load(new java.io.InputStreamReader(in, StandardCharsets.UTF_8));
                }
                for (String key : props.stringPropertyNames()) {
                    try {
                        UUID uuid = UUID.fromString(key);
                        String value = props.getProperty(key);
                        if (value != null && VALID_NAME.matcher(value).matches()) NAMES.put(uuid, value);
                    } catch (IllegalArgumentException ignored) {}
                }
            }
        } catch (IOException e) {
            server.getLogger().error("VexName Bridge could not load names", e);
        }
    }

    private static void save() {
        if (saveFile == null) return;
        Properties props = new Properties();
        for (var entry : NAMES.entrySet()) props.setProperty(entry.getKey().toString(), entry.getValue());
        try {
            Path tmp = saveFile.resolveSibling("names.properties.tmp");
            try (var out = Files.newOutputStream(tmp)) {
                props.store(new java.io.OutputStreamWriter(out, StandardCharsets.UTF_8));
            }
            Files.move(tmp, saveFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (Exception e) {
            try {
                Files.move(saveFile.resolveSibling("names.properties.tmp"), saveFile, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception ignored) {}
        }
    }
}
