package com.Doctoor.app.rx.functions;

import io.reactivex.functions.Action;

/**
 * Like {@link Action} but without Exception
 */

public interface PlainAction extends Action {

    /**
     * Run the action
     */
    @Override
    void run();
}
