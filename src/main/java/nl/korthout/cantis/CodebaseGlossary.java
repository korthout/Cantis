package nl.korthout.cantis;

import java.util.stream.Stream;

import lombok.NonNull;

/**
 * A list of definitions in a codebase.
 */
@GlossaryTerm
public final class CodebaseGlossary implements Glossary {

    private final Stream<Classifier> classifiers;

    /**
     * Constructor.
     * @param codebase The codebase containing classifiers
     */
    CodebaseGlossary(Codebase codebase) {
        this(codebase.classifiers());
    }

    /**
     * Constructor.
     * @param classifiers The classifiers for which to build definitions
     */
    CodebaseGlossary(@NonNull Stream<Classifier> classifiers) {
        this.classifiers = classifiers;
    }

    /**
     * Builds glossary definitions for this {@link Codebase}.
     * @return {@link GlossaryTerm} annotated {@link Classifier}s as definitions.
     */
    @Override
    public Stream<Definition> definitions() {
        return classifiers
            .filter(Classifier::hasGlossaryTermAnnotation)
            .filter(Classifier::hasJavadoc)
            .map(Classifier::definition);
    }

}
