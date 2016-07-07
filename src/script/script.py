#!/usr/bin/python
# -*- coding: utf-8 -*-

import sys

import nltk
import pickle

from collections import defaultdict
from miopia.preprocessor.PreProcessor import PreProcessor
from miopia.preparator.LexicalProcessor import LexicalProcessor
from miopia.preparator.TextPreparator import TextPreparator
from miopia.parser.Parser import Parser
from miopia.analyzer.Dictionary import Dictionary
from miopia.analyzer.SentimentAnalyzer import SentimentAnalyzer
from miopia.analyzer.AnalyzerConfiguration import AnalyzerConfiguration
from miopia.util.ConfigurationManager import ConfigurationManager
from miopia.classifier.SimpleClassifier import SimpleClassifier
from miopia.classifier.TernaryStrategy import TernaryStrategy
from nltk.tokenize.punkt import PunktWordTokenizer
from miopia.classifier.PolarityType import PolarityType
import os

PATH_DUMMY_TRAINING_SET = "/usr/local/lib/python2.7/dist-packages/miopia-0.1.0-py2.7.egg/miopia/dataresources/dummy_training_set/"

preprocessor = PreProcessor()
preparator = TextPreparator()
sentence_tokenizer = nltk.data.load('tokenizers/punkt/spanish.pickle')
tokenizer = PunktWordTokenizer()
tagger = pickle.load(open(ConfigurationManager().getParameter("path_pickle_taggers")+"spanish_brill.pickle",'r'))
parser = Parser()
dictionary = Dictionary()
lexical_processor = LexicalProcessor(sentence_tokenizer,
                                     tokenizer,
                                     tagger)
s =  SentimentAnalyzer(parser,
                       dictionary,
                       AnalyzerConfiguration(final_sentences_weight=1.),
                       preprocessor,
                       lexical_processor)

unsupervised_classifier = SimpleClassifier(s,TernaryStrategy(0.),[],None)

total_oraciones_etiquetadas = []
sentimientos_tweets = []
frases_por_tweet = []
frases_por_tweet.extend([0])
tweets = []
sentiments_list = []

path=sys.argv[1]

file = open(path, 'r')

for line in file:
    tweets.extend([line])

for tweet in tweets:
	preprocessed_text = preprocessor.preprocess(tweet)
	sentences =lexical_processor.extract_sentences(preprocessed_text)
	(tokens,lsi) = lexical_processor.extract_tokens(sentences)
	tagged_sentences = lexical_processor.extract_tags(tokens)
	l=len(tagged_sentences)
	frases_por_tweet.extend([frases_por_tweet[len(frases_por_tweet)-1]+l])
	total_oraciones_etiquetadas.extend(tagged_sentences)

dependency_graphs = parser.parse(total_oraciones_etiquetadas)

i=0
while (i<len(frases_por_tweet)-1):
	if (frases_por_tweet[i+1]-frases_por_tweet[i]>1):
		lists_graphs,sentiment_summary = s._analyze_graphs(dependency_graphs[frases_por_tweet[i]:(frases_por_tweet[i+1]-1)],None)
		sentimientos_tweets.extend([sentiment_summary])
	else:
		lists_graphs,sentiment_summary = s._analyze_graphs([dependency_graphs[frases_por_tweet[i]]],None)
		sentimientos_tweets.extend([sentiment_summary])
	i=i+1

for sentimiento in sentimientos_tweets:
	sentiments_list.extend([unsupervised_classifier.classify_from_info(sentimiento)])

print sentiments_list



