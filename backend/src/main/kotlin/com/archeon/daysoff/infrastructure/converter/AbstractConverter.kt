package com.archeon.daysoff.infrastructure.converter

import com.archeon.daysoff.business.port.converter.ResourceConverter

abstract class AbstractConverter<DOMAIN, RESOURCE> : ResourceConverter<DOMAIN, RESOURCE>
