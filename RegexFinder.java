///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS info.picocli:picocli:4.7.5

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Command(name = "RegexFinder", 
         mixinStandardHelpOptions = true, 
         version = "1.0",
         description = "Finds files matching a regex pattern, similar to Unix find")
public class RegexFinder implements Callable<Integer> {

    @Parameters(index = "0", description = "The directory to start searching from")
    private File startDir;

    @Option(names = {"-p", "--pattern"}, description = "Regex pattern to match filenames")
    private String pattern;

    @Option(names = {"-r", "--recursive"}, description = "Search directories recursively")
    private boolean recursive = true;

    @Option(names = {"-d", "--directories-only"}, description = "Match only directories")
    private boolean directoriesOnly = false;

    @Option(names = {"-f", "--files-only"}, description = "Match only files")
    private boolean filesOnly = false;

    @Option(names = {"-i", "--ignore-case"}, description = "Ignore case when matching")
    private boolean ignoreCase = false;

    @Option(names = {"-e", "--exact"}, description = "Exact match (entire filename must match pattern)")
    private boolean exactMatch = false;

    public static void main(String... args) {
        int exitCode = new CommandLine(new RegexFinder()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        if (startDir == null || !startDir.exists()) {
            System.err.println("Error: Starting directory does not exist");
            return 1;
        }

        if (pattern == null) {
            System.err.println("Error: Regex pattern is required");
            return 1;
        }

        // Compile regex pattern with case sensitivity option
        Pattern regexPattern = ignoreCase 
            ? Pattern.compile(pattern, Pattern.CASE_INSENSITIVE) 
            : Pattern.compile(pattern);
            
        int flags = recursive ? Integer.MAX_VALUE : 1;

        try (Stream<Path> paths = Files.walk(startDir.toPath(), flags)) {
            paths.filter(path -> {
                    File file = path.toFile();
                    
                    // Skip if we're only looking for directories or files
                    if (directoriesOnly && !file.isDirectory()) return false;
                    if (filesOnly && !file.isFile()) return false;
                    
                    String name = file.getName();
                    
                    // For the root directory, use the absolute path
                    if (name.isEmpty() && file.isDirectory()) {
                        name = file.getAbsolutePath();
                    }
                    
                    // Match against the pattern
                    if (exactMatch) {
                        return regexPattern.matcher(name).matches();
                    } else {
                        return regexPattern.matcher(name).find();
                    }
                })
                .forEach(path -> System.out.println(path));
        }
        
        return 0;
    }
}
