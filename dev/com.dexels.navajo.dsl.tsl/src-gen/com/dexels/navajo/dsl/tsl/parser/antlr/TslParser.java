/*
* generated by Xtext
*/
package com.dexels.navajo.dsl.tsl.parser.antlr;

import com.google.inject.Inject;

import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import com.dexels.navajo.dsl.tsl.services.TslGrammarAccess;

public class TslParser extends org.eclipse.xtext.parser.antlr.AbstractAntlrParser {
	
	@Inject
	private TslGrammarAccess grammarAccess;
	
	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_XMLCOMMENT");
	}
	
	@Override
	protected com.dexels.navajo.dsl.tsl.parser.antlr.internal.InternalTslParser createParser(XtextTokenStream stream) {
		return new com.dexels.navajo.dsl.tsl.parser.antlr.internal.InternalTslParser(stream, getGrammarAccess());
	}
	
	@Override 
	protected String getDefaultRuleName() {
		return "Tml";
	}
	
	public TslGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(TslGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
}
