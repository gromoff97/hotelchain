package org.gromovhotels.hotelchain.utils.shell;

import asg.cliche.ConsoleIO;
import asg.cliche.Shell;
import asg.cliche.Shell.Settings;
import asg.cliche.TokenException;
import asg.cliche.util.EmptyMultiMap;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

import static asg.cliche.ShellFactory.createConsoleShell;
import static java.nio.charset.StandardCharsets.UTF_8;

public final class ShellExecutor {

    private static final String DEFAULT_PROMPT = "hotelchain";
    private static final String GREETING_TEXT = "Добро пожаловать. Введите '?l', чтобы показать доступные команды";

    @SneakyThrows
    public static void executeShellDrivenBy(Object mainHandler) {
        Shell shell = createConsoleShell(DEFAULT_PROMPT, GREETING_TEXT, mainHandler);
        ConsoleIO io = new FixedConsoleIo(
                new BufferedReader(new InputStreamReader(System.in, UTF_8)),
                new PrintStream(System.out, true, UTF_8),
                new PrintStream(System.err, true, UTF_8)
        );
        shell.setSettings(new Settings(io, io, new EmptyMultiMap<>(), false));
        shell.commandLoop();
    }

    private static class FixedConsoleIo extends ConsoleIO {
        private final PrintStream err;

        public FixedConsoleIo(BufferedReader in, PrintStream out, PrintStream err) {
            super(in, out, err);
            this.err = err;
        }


        @Override
        public void outputException(String input, TokenException error) {
            super.outputException(input, error);
        }

        @Override
        public void outputException(Throwable e) {
            err.println(e.getMessage());
        }
    }
}

