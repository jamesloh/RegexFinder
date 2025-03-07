# RegexFinder

A lightweight Java utility for finding files that match regex patterns, inspired by the Unix `find` command. Built with JBang for easy execution without compilation.

## Features

- Search for files and directories using regular expression patterns
- Recursive directory traversal (configurable)
- Filter by file type (files only or directories only)
- Case-sensitive or case-insensitive matching
- Exact or partial pattern matching

## Prerequisites

- Java 8 or higher
- [JBang](https://www.jbang.dev/documentation/guide/latest/installation.html) (installation instructions included below)

## Installation

1. Install JBang (if not already installed):
   ```bash
   curl -Ls https://sh.jbang.dev | bash -s - app setup
   ```

2. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/regex-finder.git
   cd regex-finder
   ```

3. Make the file executable (Unix/Linux/macOS):
   ```bash
   chmod +x RegexFinder.java
   ```

No compilation needed! RegexFinder uses JBang to run directly from source.

## Usage

Basic syntax:
```bash
jbang RegexFinder.java [directory] -p [regex-pattern] [options]
```

### Required Arguments

- First parameter: The directory to start searching from
- `-p, --pattern`: Regular expression pattern to match filenames

### Optional Arguments

- `-r, --recursive`: Search directories recursively (default: true)
- `-d, --directories-only`: Match only directories
- `-f, --files-only`: Match only files
- `-i, --ignore-case`: Ignore case when matching
- `-e, --exact`: Exact match (entire filename must match pattern)
- `-h, --help`: Show help message
- `-V, --version`: Print version information

## Examples

### Find all Java files in the current directory

```bash
jbang RegexFinder.java . -p ".*\.java$"
```

### Find all directories containing "test" in their name (case insensitive)

```bash
jbang RegexFinder.java . -p "test" -d -i
```

### Find all files matching exactly "README.md"

```bash
jbang RegexFinder.java . -p "README\.md" -f -e
```

### Search non-recursively in a specific directory

```bash
jbang RegexFinder.java /path/to/search -p "data.*\.csv$" -r false
```

### Find all log files from the home directory

```bash
jbang RegexFinder.java ~ -p ".*\.log$" -f
```

## Understanding the Pattern Matching

- By default, the utility performs a partial match using Java's regex `find()` method
- When using `-e/--exact`, it uses regex `matches()` which requires the entire filename to match the pattern
- Remember to escape special regex characters with backslashes
- For case-insensitive searches, use the `-i/--ignore-case` flag

## Technical Details

RegexFinder uses:
- [Picocli](https://picocli.info/) for command-line parsing
- Java NIO for filesystem operations
- Java Regex for pattern matching
- JBang for script execution

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## JBang Installation Details

If the quick installation method above doesn't work for your system, here are detailed installation options:

### Unix/Linux/macOS
```bash
curl -Ls https://sh.jbang.dev | bash -s - app setup
```

### Windows (PowerShell)
```powershell
iex "& { $(iwr https://ps.jbang.dev) } app setup"
```

### Manual Installation
Visit the [JBang Installation Documentation](https://www.jbang.dev/documentation/guide/latest/installation.html) for more options.
