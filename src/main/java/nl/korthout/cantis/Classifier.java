package nl.korthout.cantis;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.javaparser.ast.nodeTypes.NodeWithJavadoc;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;

import lombok.NonNull;

/**
 * A type in a Java program as declared in a .java file.
 */
@GlossaryTerm
public interface Classifier {

    /**
     * Tells whether this Classifier is annotated as glossary term.
     * @return True when this is annotated with {@link GlossaryTerm}
     */
    boolean hasGlossaryTermAnnotation();

    /**
     * Tells whether this Classifier has a Javadoc description.
     * @return True when this has a Javadoc comment at the type level
     */
    boolean hasJavadoc();

    /**
     * Builds a {@link Definition} from this classifier.
     * @return The definition
     */
    Definition definition();

    /**
     * Classifier that is created using the com.github.Javaparser library.
     */
    final class ClassifierFromJavaparser implements Classifier {

        private final NodeWithAnnotations<? extends Node> annotated;
        private final NodeWithJavadoc<ClassOrInterfaceDeclaration> documented;
        private final NodeWithSimpleName named;

        /**
         * Constructor.
         * @param declaration This classifier's declaration
         */
        public ClassifierFromJavaparser(ClassOrInterfaceDeclaration declaration) {
            this(declaration, declaration, declaration);
        }

        /**
         * Constructor.
         * @param annotated The annotated part of the classifier
         * @param documented The documented part of the classifier
         * @param named The named part of the classifier
         */
        public ClassifierFromJavaparser(
            @NonNull NodeWithAnnotations<? extends Node> annotated,
            @NonNull NodeWithJavadoc<ClassOrInterfaceDeclaration> documented,
            @NonNull NodeWithSimpleName named
        ) {
            this.annotated = annotated;
            this.documented = documented;
            this.named = named;
        }

        @Override
        public boolean hasGlossaryTermAnnotation() {
            return annotated.isAnnotationPresent(GlossaryTerm.class.getSimpleName());
        }

        @Override
        public boolean hasJavadoc() {
            return documented.hasJavaDocComment();
        }

        @Override
        public Definition definition() {
            var term = named.getNameAsString();
            var description = documented.getJavadocComment()
                .orElseGet(() -> new JavadocComment("Description not found"))
                .parse().getDescription().toText();
            return new Definition(term, description);
        }
    }
}
