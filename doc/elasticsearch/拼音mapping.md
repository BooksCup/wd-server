### 1.创建index:
``` java
PUT /file_item
{
	"settings": {
		"analysis": {
			"analyzer": {
				"pinyin_analyzer": {
					"tokenizer": "my_ngram",
					"filter": [
						"pinyin_filter"
					]
				}
			},
			"tokenizer": {
				"my_ngram": {
					"type": "ngram",
					"min_gram": 1,
					"max_gram": 50,
					"token_chars": [
						"letter",
						"digit",
						"punctuation",
						"symbol"
					]
				}
			},
			"filter": {
				"pinyin_filter": {
					"type": "pinyin",
					"keep_full_pinyin": false,
					"keep_joined_full_pinyin": true,
					"keep_none_chinese_in_joined_full_pinyin": true,
					"none_chinese_pinyin_tokenize": false,
					"remove_duplicated_term": true
				}
			}
		}
	}
}
```
### 2.修改mapping:
``` java
PUT /file_item/file_item/_mapping  
{
	"file_item": {
		"properties": {
			"diskName": {
				"type": "text",
				"fields": {
					"keyword": {
						"type": "keyword",
						"ignore_above": 256
					}
				}
			},
			"fileName": {
				"type": "text",
				"fields": {
					"keyword": {
						"type": "keyword",
						"ignore_above": 256
					},
					"pinyin": {
						"type": "text",
						"analyzer": "pinyin_analyzer",
						"search_analyzer": "standard",
						"term_vector": "with_positions_offsets"
					}
				}
			},
			"filePath": {
				"type": "text",
				"fields": {
					"keyword": {
						"type": "keyword",
						"ignore_above": 256
					},
					"pinyin": {
						"type": "text",
						"analyzer": "pinyin_analyzer",
						"search_analyzer": "standard",
						"term_vector": "with_positions_offsets"
					}
				}
			},
			"fileSize": {
				"type": "text",
				"fields": {
					"keyword": {
						"type": "keyword",
						"ignore_above": 256
					}
				}
			},
			"id": {
				"type": "text",
				"fields": {
					"keyword": {
						"type": "keyword",
						"ignore_above": 256
					}
				}
			}
		}
	}
}
```
