package de.matrixweb.harhar.junit


import org.junit.runner.Description
import org.junit.runner.notification.RunNotifier
import org.junit.runners.ParentRunner
import org.junit.runners.model.FrameworkMethod

class HarHarRunner extends ParentRunner<FrameworkMethod> {

  HarHarRunner(Class klass) {
    super(klass)
  }

  List<FrameworkMethod> getChildren() {
    testClass.getAnnotatedMethods(Har.class)
  }

  Description describeChild(FrameworkMethod child) {
    Description.createTestDescription(getTestClass().getJavaClass(),
        child.name, child.annotations);
  }

  void runChild(FrameworkMethod child, RunNotifier notifier) {
    def resultInvocation = { child.invokeExplosively(testClass.onlyConstructor.newInstance(), it) }
    def statement = new RunHarStatement(resultInvocation, child.getAnnotation(Har.class))
    runLeaf(statement, describeChild(child), notifier)
  }
}
