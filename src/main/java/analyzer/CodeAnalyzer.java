package analyzer;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;

import java.util.Arrays;
import java.util.List;

/**
 * A customized analyzer for Python source code
 */
public class CodeAnalyzer extends Analyzer {
    private final List<String> pythonstopWords = Arrays.asList(
            "False", "None", "True", "and", "as", "assert", "break",
            "class", "continue", "def", "del", "elif", "else", "except",
            "finally", "for", "from", "global", "if", "import", "in", "is",
            "lambda", "nonlocal", "not", "or", "pass", "raise", "return",
            "try", "while", "with", "yield", "print"
    );

    public CodeAnalyzer() {
        super();
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer tokenizer = new LowerCaseTokenizer();
        return new TokenStreamComponents(tokenizer, new StopFilter(tokenizer, new CharArraySet(pythonstopWords, false)));
    }
}
