package nl.korthout.cantis;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.javaparser.ast.nodeTypes.NodeWithJavadoc;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;

import nl.korthout.cantis.Classifier.ClassifierFromJavaparser;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class ClassifierFromJavaparserTest {

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNullAnnotated() {
        new ClassifierFromJavaparser(
            null,
            new FakeNodeWithJavadoc(),
            new FakeNodeWithSimpleName()
        );
    }

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNullDocumented() {
        new ClassifierFromJavaparser(
            new FakeNodeWithAnnotations(),
            null,
            new FakeNodeWithSimpleName()
        );
    }

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNullNamed() {
        new ClassifierFromJavaparser(
            new FakeNodeWithAnnotations(),
            new FakeNodeWithJavadoc(),
            null
        );
    }

    @Test
    public void classifierCanHaveJavadoc() {
        assertThat(
            new ClassifierFromJavaparser(
                new FakeNodeWithAnnotations(),
                new FakeNodeWithJavadoc(),
                new FakeNodeWithSimpleName()
            ).hasJavadoc()
        ).isTrue();
    }

    @Test
    public void classifierDoesNotRequireJavadoc() {
        assertThat(
            new ClassifierFromJavaparser(
                new FakeNodeWithAnnotations(),
                new FakeNodeWithoutJavadoc(),
                new FakeNodeWithSimpleName()
            ).hasJavadoc()
        ).isFalse();
    }

    @Test
    public void classifierCanHaveAnnotations() {
        assertThat(
            new ClassifierFromJavaparser(
                new FakeNodeWithAnnotations(),
                new FakeNodeWithJavadoc(),
                new FakeNodeWithSimpleName()
            ).hasGlossaryTermAnnotation()
        ).isTrue();
    }

    @Test
    public void classifierDoNotRequireAnnotations() {
        assertThat(
            new ClassifierFromJavaparser(
                new FakeNodeWithoutAnnotations(),
                new FakeNodeWithJavadoc(),
                new FakeNodeWithSimpleName()
            ).hasGlossaryTermAnnotation()
        ).isFalse();
    }

    @Test
    public void classifierCanBeDescribedByADefinition() {
        assertThat(
            new ClassifierFromJavaparser(
                new FakeNodeWithAnnotations(),
                new FakeNodeWithJavadoc(),
                new FakeNodeWithSimpleName()
            ).definition()
        ).isEqualTo(
            new Definition("FakeClassifier", "Acts as a classifier with Javadoc.")
        );
    }

    /**
     * Node is annotated with @GlossaryTerm.
     */
    private class FakeNodeWithAnnotations implements NodeWithAnnotations {
        @Override
        public NodeList<AnnotationExpr> getAnnotations() {
            return new NodeList<>(
                new MarkerAnnotationExpr("GlossaryTerm")
            );
        }

        @Override
        public void tryAddImportToParentCompilationUnit(Class clazz) {
            // not necessary to implement
        }

        @Override
        public Node setAnnotations(NodeList annotations) {
            return null;
        }
    }

    /**
     * Node is not annotated.
     */
    private class FakeNodeWithoutAnnotations implements NodeWithAnnotations {
        @Override
        public NodeList<AnnotationExpr> getAnnotations() {
            return new NodeList<>();
        }

        @Override
        public void tryAddImportToParentCompilationUnit(Class clazz) {
            // not necessary to implement
        }

        @Override
        public Node setAnnotations(NodeList annotations) {
            return null;
        }
    }

    /**
     * Node has Javadoc description: Acts as a classifier with Javadoc.
     */
    private class FakeNodeWithJavadoc implements NodeWithJavadoc<ClassOrInterfaceDeclaration> {
        @Override
        public Optional<Comment> getComment() {
            return Optional.of(new JavadocComment("Acts as a classifier with Javadoc."));
        }
        @Override
        public Node setComment(Comment comment) {
            return null;
        }
    }

    /**
     * Node does not have a Javadoc description.
     */
    private class FakeNodeWithoutJavadoc implements NodeWithJavadoc<ClassOrInterfaceDeclaration> {
        @Override
        public Optional<Comment> getComment() {
            return Optional.empty();
        }
        @Override
        public Node setComment(Comment comment) {
            return null;
        }
    }

    /**
     * Node has the name 'FakeClassifier'.
     */
    private class FakeNodeWithSimpleName implements NodeWithSimpleName {
        @Override
        public SimpleName getName() {
            return new SimpleName("FakeClassifier");
        }

        @Override
        public Node setName(SimpleName name) {
            return null;
        }
    }
}