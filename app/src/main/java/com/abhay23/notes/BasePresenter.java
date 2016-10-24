package com.abhay23.notes;

import android.support.annotation.CallSuper;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter {
  private final CompositeSubscription compositeSubscription;

  public BasePresenter() {
    compositeSubscription = new CompositeSubscription();
  }

  @CallSuper protected void unsubscribe() {
    compositeSubscription.clear();
  }

  protected void addSubscription(Subscription subscription) {
    compositeSubscription.add(subscription);
  }
}
