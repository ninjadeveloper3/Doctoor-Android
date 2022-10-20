package com.Doctoor.app.di.qualifiers;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Activity fragment manager qualifier
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityFragmentManager {}
