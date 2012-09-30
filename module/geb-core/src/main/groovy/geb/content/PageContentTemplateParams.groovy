/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package geb.content

import geb.Page
import geb.error.InvalidPageContent

class PageContentTemplateParams {

	final boolean required
	final boolean cache
	List<Class<? extends Page>> toList
	Class<? extends Page> toSingle
	Class<? extends Page> page
	final wait

	PageContentTemplateParams(PageContentTemplate owner, Map<String, ?> params) {
		required = params.required
		cache = params.cache

		def toParam = params.to
		if (!toParam) {
			toSingle = null
			toList = null
		} else if (toParam instanceof Class && Page.isAssignableFrom(toParam)) {
			toSingle = toParam
			toList = null
		} else if (toParam instanceof List) {
			toSingle = null
			toList = toParam
		} else {
			throw new InvalidPageContent("'to' content parameter should be a class that extends Page or a list of classes that extend Page, but it isn't for $owner: $toParam")
		}

		def pageParam = params.page
		if (pageParam && (!(pageParam instanceof Class) || !Page.isAssignableFrom(pageParam))) {
			throw new InvalidPageContent("'page' content parameter should be a class that extends Page but it isn't for $owner: $pageParam")
		}
		page = pageParam as Class<? extends Page>

		wait = params.wait
	}

}