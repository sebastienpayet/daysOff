import {Directive, EmbeddedViewRef, Input, TemplateRef, ViewContainerRef} from '@angular/core';

@Directive({
  selector: '[appRefreshViewOnChange]'
})

export class RefreshViewOnChangeDirective {
  refreshViewContext = new RefreshViewContext();
  private viewRef: EmbeddedViewRef<RefreshViewContext> | null = null;

  constructor(
    private readonly viewContainer: ViewContainerRef,
    private readonly templateRef: TemplateRef<RefreshViewContext>
  ) {
  }

  @Input('appRefreshViewOnChange')
  set refreshView(expr: any) {
    this.refreshViewContext.refreshView = expr;
    this.updateView();
  }

  private updateView(): void {
    if (!this.viewRef) {
      this.viewContainer.clear();
      this.viewRef = this.viewContainer.createEmbeddedView(this.templateRef, this.refreshViewContext);
    }
  }
}

export class RefreshViewContext {
  public refreshView: any = null;
}
