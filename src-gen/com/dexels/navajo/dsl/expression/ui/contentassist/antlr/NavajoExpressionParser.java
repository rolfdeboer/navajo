/*
* generated by Xtext
*/
package com.dexels.navajo.dsl.expression.ui.contentassist.antlr;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.AbstractContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;

import com.google.inject.Inject;

import com.dexels.navajo.dsl.expression.services.NavajoExpressionGrammarAccess;

public class NavajoExpressionParser extends AbstractContentAssistParser {
	
	@Inject
	private NavajoExpressionGrammarAccess grammarAccess;
	
	private Map<AbstractElement, String> nameMappings;
	
	@Override
	protected com.dexels.navajo.dsl.expression.ui.contentassist.antlr.internal.InternalNavajoExpressionParser createParser() {
		com.dexels.navajo.dsl.expression.ui.contentassist.antlr.internal.InternalNavajoExpressionParser result = new com.dexels.navajo.dsl.expression.ui.contentassist.antlr.internal.InternalNavajoExpressionParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}
	
	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getPathElementAccess().getAlternatives(), "rule__PathElement__Alternatives");
					put(grammarAccess.getEqualityExpressionAccess().getAlternatives_1(), "rule__EqualityExpression__Alternatives_1");
					put(grammarAccess.getAdditiveExpressionAccess().getAlternatives_1(), "rule__AdditiveExpression__Alternatives_1");
					put(grammarAccess.getMultiplicativeExpressionAccess().getAlternatives_1(), "rule__MultiplicativeExpression__Alternatives_1");
					put(grammarAccess.getUnaryExpressionAccess().getAlternatives(), "rule__UnaryExpression__Alternatives");
					put(grammarAccess.getPrimaryExpressionAccess().getAlternatives(), "rule__PrimaryExpression__Alternatives");
					put(grammarAccess.getLiteralAccess().getAlternatives(), "rule__Literal__Alternatives");
					put(grammarAccess.getTmlExpressionAccess().getGroup(), "rule__TmlExpression__Group__0");
					put(grammarAccess.getTmlExpressionAccess().getGroup_3(), "rule__TmlExpression__Group_3__0");
					put(grammarAccess.getExistsTmlExpressionAccess().getGroup(), "rule__ExistsTmlExpression__Group__0");
					put(grammarAccess.getExistsTmlExpressionAccess().getGroup_4(), "rule__ExistsTmlExpression__Group_4__0");
					put(grammarAccess.getMapGetReferenceAccess().getGroup(), "rule__MapGetReference__Group__0");
					put(grammarAccess.getMapGetReferenceAccess().getGroup_2(), "rule__MapGetReference__Group_2__0");
					put(grammarAccess.getOrExpressionAccess().getGroup(), "rule__OrExpression__Group__0");
					put(grammarAccess.getOrExpressionAccess().getGroup_1(), "rule__OrExpression__Group_1__0");
					put(grammarAccess.getAndExpressionAccess().getGroup(), "rule__AndExpression__Group__0");
					put(grammarAccess.getAndExpressionAccess().getGroup_1(), "rule__AndExpression__Group_1__0");
					put(grammarAccess.getEqualityExpressionAccess().getGroup(), "rule__EqualityExpression__Group__0");
					put(grammarAccess.getEqualityExpressionAccess().getGroup_1_0(), "rule__EqualityExpression__Group_1_0__0");
					put(grammarAccess.getEqualityExpressionAccess().getGroup_1_1(), "rule__EqualityExpression__Group_1_1__0");
					put(grammarAccess.getAdditiveExpressionAccess().getGroup(), "rule__AdditiveExpression__Group__0");
					put(grammarAccess.getAdditiveExpressionAccess().getGroup_1_0(), "rule__AdditiveExpression__Group_1_0__0");
					put(grammarAccess.getAdditiveExpressionAccess().getGroup_1_1(), "rule__AdditiveExpression__Group_1_1__0");
					put(grammarAccess.getMultiplicativeExpressionAccess().getGroup(), "rule__MultiplicativeExpression__Group__0");
					put(grammarAccess.getMultiplicativeExpressionAccess().getGroup_1_0(), "rule__MultiplicativeExpression__Group_1_0__0");
					put(grammarAccess.getMultiplicativeExpressionAccess().getGroup_1_1(), "rule__MultiplicativeExpression__Group_1_1__0");
					put(grammarAccess.getUnaryExpressionAccess().getGroup_0(), "rule__UnaryExpression__Group_0__0");
					put(grammarAccess.getPrimaryExpressionAccess().getGroup_1(), "rule__PrimaryExpression__Group_1__0");
					put(grammarAccess.getFunctionCallAccess().getGroup(), "rule__FunctionCall__Group__0");
					put(grammarAccess.getFunctionCallAccess().getGroup_3(), "rule__FunctionCall__Group_3__0");
					put(grammarAccess.getLiteralAccess().getGroup_0(), "rule__Literal__Group_0__0");
					put(grammarAccess.getLiteralAccess().getGroup_2(), "rule__Literal__Group_2__0");
					put(grammarAccess.getLiteralAccess().getGroup_4(), "rule__Literal__Group_4__0");
					put(grammarAccess.getLiteralAccess().getGroup_4_2(), "rule__Literal__Group_4_2__0");
					put(grammarAccess.getTopLevelAccess().getToplevelExpressionAssignment(), "rule__TopLevel__ToplevelExpressionAssignment");
					put(grammarAccess.getTmlExpressionAccess().getAbsoluteAssignment_1(), "rule__TmlExpression__AbsoluteAssignment_1");
					put(grammarAccess.getTmlExpressionAccess().getElementsAssignment_2(), "rule__TmlExpression__ElementsAssignment_2");
					put(grammarAccess.getTmlExpressionAccess().getElementsAssignment_3_1(), "rule__TmlExpression__ElementsAssignment_3_1");
					put(grammarAccess.getExistsTmlExpressionAccess().getAbsoluteAssignment_2(), "rule__ExistsTmlExpression__AbsoluteAssignment_2");
					put(grammarAccess.getExistsTmlExpressionAccess().getElementsAssignment_3(), "rule__ExistsTmlExpression__ElementsAssignment_3");
					put(grammarAccess.getExistsTmlExpressionAccess().getElementsAssignment_4_1(), "rule__ExistsTmlExpression__ElementsAssignment_4_1");
					put(grammarAccess.getMapGetReferenceAccess().getOperationsAssignment_0(), "rule__MapGetReference__OperationsAssignment_0");
					put(grammarAccess.getMapGetReferenceAccess().getElementsAssignment_1(), "rule__MapGetReference__ElementsAssignment_1");
					put(grammarAccess.getMapGetReferenceAccess().getElementsAssignment_2_1(), "rule__MapGetReference__ElementsAssignment_2_1");
					put(grammarAccess.getOrExpressionAccess().getParametersAssignment_0(), "rule__OrExpression__ParametersAssignment_0");
					put(grammarAccess.getOrExpressionAccess().getOperationsAssignment_1_0(), "rule__OrExpression__OperationsAssignment_1_0");
					put(grammarAccess.getOrExpressionAccess().getParametersAssignment_1_1(), "rule__OrExpression__ParametersAssignment_1_1");
					put(grammarAccess.getAndExpressionAccess().getParametersAssignment_0(), "rule__AndExpression__ParametersAssignment_0");
					put(grammarAccess.getAndExpressionAccess().getOperationsAssignment_1_0(), "rule__AndExpression__OperationsAssignment_1_0");
					put(grammarAccess.getAndExpressionAccess().getParametersAssignment_1_1(), "rule__AndExpression__ParametersAssignment_1_1");
					put(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_0(), "rule__EqualityExpression__ParametersAssignment_0");
					put(grammarAccess.getEqualityExpressionAccess().getOperationsAssignment_1_0_0(), "rule__EqualityExpression__OperationsAssignment_1_0_0");
					put(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_1_0_1(), "rule__EqualityExpression__ParametersAssignment_1_0_1");
					put(grammarAccess.getEqualityExpressionAccess().getOperationsAssignment_1_1_0(), "rule__EqualityExpression__OperationsAssignment_1_1_0");
					put(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_1_1_1(), "rule__EqualityExpression__ParametersAssignment_1_1_1");
					put(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_0(), "rule__AdditiveExpression__ParametersAssignment_0");
					put(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_1_0_1(), "rule__AdditiveExpression__ParametersAssignment_1_0_1");
					put(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_1_1_1(), "rule__AdditiveExpression__ParametersAssignment_1_1_1");
					put(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_0(), "rule__MultiplicativeExpression__ParametersAssignment_0");
					put(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAssignment_1_0_0(), "rule__MultiplicativeExpression__OperationsAssignment_1_0_0");
					put(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_1_0_1(), "rule__MultiplicativeExpression__ParametersAssignment_1_0_1");
					put(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAssignment_1_1_0(), "rule__MultiplicativeExpression__OperationsAssignment_1_1_0");
					put(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_1_1_1(), "rule__MultiplicativeExpression__ParametersAssignment_1_1_1");
					put(grammarAccess.getUnaryExpressionAccess().getOperationsAssignment_0_0(), "rule__UnaryExpression__OperationsAssignment_0_0");
					put(grammarAccess.getUnaryExpressionAccess().getParametersAssignment_0_1(), "rule__UnaryExpression__ParametersAssignment_0_1");
					put(grammarAccess.getPrimaryExpressionAccess().getParametersAssignment_0(), "rule__PrimaryExpression__ParametersAssignment_0");
					put(grammarAccess.getPrimaryExpressionAccess().getParametersAssignment_1_1(), "rule__PrimaryExpression__ParametersAssignment_1_1");
					put(grammarAccess.getFunctionCallAccess().getNameAssignment_0(), "rule__FunctionCall__NameAssignment_0");
					put(grammarAccess.getFunctionCallAccess().getParametersAssignment_2(), "rule__FunctionCall__ParametersAssignment_2");
					put(grammarAccess.getFunctionCallAccess().getParametersAssignment_3_1(), "rule__FunctionCall__ParametersAssignment_3_1");
					put(grammarAccess.getLiteralAccess().getValueStringAssignment_1(), "rule__Literal__ValueStringAssignment_1");
					put(grammarAccess.getLiteralAccess().getOperationsAssignment_2_0(), "rule__Literal__OperationsAssignment_2_0");
					put(grammarAccess.getLiteralAccess().getValueStringAssignment_2_2(), "rule__Literal__ValueStringAssignment_2_2");
					put(grammarAccess.getLiteralAccess().getParametersAssignment_2_4(), "rule__Literal__ParametersAssignment_2_4");
					put(grammarAccess.getLiteralAccess().getExpressionTypeAssignment_4_0(), "rule__Literal__ExpressionTypeAssignment_4_0");
					put(grammarAccess.getLiteralAccess().getParametersAssignment_4_1(), "rule__Literal__ParametersAssignment_4_1");
					put(grammarAccess.getLiteralAccess().getParametersAssignment_4_2_1(), "rule__Literal__ParametersAssignment_4_2_1");
					put(grammarAccess.getLiteralAccess().getElementsAssignment_5(), "rule__Literal__ElementsAssignment_5");
					put(grammarAccess.getLiteralAccess().getElementsAssignment_6(), "rule__Literal__ElementsAssignment_6");
					put(grammarAccess.getLiteralAccess().getElementsAssignment_7(), "rule__Literal__ElementsAssignment_7");
					put(grammarAccess.getLiteralAccess().getElementsAssignment_8(), "rule__Literal__ElementsAssignment_8");
					put(grammarAccess.getLiteralAccess().getParametersAssignment_9(), "rule__Literal__ParametersAssignment_9");
					put(grammarAccess.getLiteralAccess().getParametersAssignment_10(), "rule__Literal__ParametersAssignment_10");
					put(grammarAccess.getLiteralAccess().getParametersAssignment_11(), "rule__Literal__ParametersAssignment_11");
				}
			};
		}
		return nameMappings.get(element);
	}
	
	@Override
	protected Collection<FollowElement> getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			com.dexels.navajo.dsl.expression.ui.contentassist.antlr.internal.InternalNavajoExpressionParser typedParser = (com.dexels.navajo.dsl.expression.ui.contentassist.antlr.internal.InternalNavajoExpressionParser) parser;
			typedParser.entryRuleTopLevel();
			return typedParser.getFollowElements();
		} catch(RecognitionException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}
	
	public NavajoExpressionGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(NavajoExpressionGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
