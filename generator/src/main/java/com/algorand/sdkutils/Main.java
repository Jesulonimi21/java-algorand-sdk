package com.algorand.sdkutils;

import com.algorand.sdkutils.generators.OpenApiParser;
import com.algorand.sdkutils.generators.ResponseGenerator;
import com.algorand.sdkutils.generators.Utils;
import com.algorand.sdkutils.listeners.Publisher;
import com.beust.jcommander.*;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.FileInputStream;

public class Main {

    /**
     * Main function. Setup argument parser and direct results to the appropriate handler.
     */
    public static void main (String argv[]) throws Exception {
        // Empty argument objects.
        CommonArgs common = new CommonArgs();
        JavaGeneratorArgs java = new JavaGeneratorArgs();
        ResponseGeneratorArgs responses = new ResponseGeneratorArgs();

        // Collect configuration and parse arguments.
        JCommander root = JCommander.newBuilder()
                .addObject(common)
                //.addCommand("go", go)
                .addCommand("java", java)
                .addCommand("responses", responses)
                .build();
        root.parse(argv);

        // Root level help generated from parameter annotations.
        if (common.help) {
            root.usage();
            return;
        }

        // Route to command handler.
        String commandName = root.getParsedCommand();
        JCommander command = root.getCommands().get(commandName);
        switch(commandName) {
            case "java":
                javaGenerator(java, command);
                return;
            case "responses":
                responseGenerator(responses, command);
                return;
        }
    }

    public static class CommonArgs {
        @Parameter(names = {"-h", "--help"}, help = true)
        boolean help = false;

        @Parameter(names = {"-s", "--specfile"}, description = "Name of the client class. i.e. IndexerClient.")
        File specfile;
    }

    @Parameters(commandDescription = "Generate the Java Client SDK.")
    public static class JavaGeneratorArgs extends CommonArgs {
        @Parameter(required = true, names = {"-n", "--client-name"}, description = "Full path to the OpenAPI v2 spec file.")
        String clientName;

        @Parameter(required = true, names = {"-m", "--model-path"}, description = "Path where generated model files will be placed.")
        String modelPath;

        @Parameter(required = true, names = {"-mp", "--model-package"}, description = "Package name to put at the top of generated model files.")
        String modelPackage;

        @Parameter(required = true, names = {"-p", "--paths-path"}, description = "Path where generated paths files will be placed.")
        String pathsPath;

        @Parameter(required = true, names = {"-pp", "--paths-package"}, description = "Package name to put at the top of generated paths files.")
        String pathsPackage;

        @Parameter(required = true, names = {"-c", "--common-path"}, description = "Path where generated client files will be placed.")
        String commonPath;

        @Parameter(required = true, names = {"-cp", "--common-package"}, description = "Package name to put at the top of generated client files.")
        String commonPackage;

        @Parameter(required = true, names = {"-t", "--token-name"}, description = "Name of the token used for this application. i.e. X-Algo-API-Token.")
        String tokenName;

        @Parameter(names = {"-tr", "--token-required"}, description = "Whether or not a no-token version of the constructor should be created.")
        Boolean tokenRequired = false;
    }

    @Parameters(commandDescription = "Generate response test file(s).")
    public static class ResponseGeneratorArgs extends CommonArgs {
        @Parameter(names = {"-f", "--filter"}, description = "Only generate response files for types matching this filter regex.")
        String filter;
    }

    /**
     * Validate common arguments.
     */
    private static void commonValidations(CommonArgs args, JCommander command) {
        if (args.help) {
            command.usage();
            System.exit(0);
        }

        if (args.specfile == null) {
            throw new RuntimeException("Specfile must be provided with -s or --specfile");
        }

        if (!args.specfile.canRead()) {
            throw new RuntimeException("Cannot read file: " + args.specfile.getAbsolutePath());
        }
    }

    /**
     * Route command to response generator.
     */
    private static void responseGenerator(ResponseGeneratorArgs args, JCommander command) throws Exception {
        commonValidations(args, command);

        JsonNode root;
        try (FileInputStream fis = new FileInputStream(args.specfile)) {
            root = Utils.getRoot(fis);
        }

        Publisher pub = new Publisher();
        ResponseGenerator subscriber = new ResponseGenerator(pub);
        OpenApiParser parser = new OpenApiParser(root, pub);
        parser.parse();
    }

    /**
     * Route command to java generator.
     */
    private static void javaGenerator(JavaGeneratorArgs args, JCommander command) {
        commonValidations(args, command);

        try {
            Generate.Generate(
                    args.clientName,
                    args.specfile,
                    args.modelPath,
                    args.modelPackage,
                    args.pathsPath,
                    args.pathsPackage,
                    args.commonPath,
                    args.commonPackage,
                    args.tokenName,
                    args.tokenRequired,
                    null);
        } catch (Exception e) {
            System.out.println("Problem generating code:" + e.getMessage());
            System.out.println("\n\n");
            e.printStackTrace();
            System.out.println("\n\n");

            command.usage();
            System.exit(0);
        }
    }
}
