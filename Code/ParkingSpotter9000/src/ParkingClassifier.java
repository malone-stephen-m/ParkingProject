// Generated with Weka 3.6.13
//
// This code is public domain and comes with no warranty.
//
// Timestamp: Tue Mar 29 10:46:08 EDT 2016



import weka.core.Attribute;
import weka.core.Capabilities;
import weka.core.Capabilities.Capability;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.RevisionUtils;
import weka.classifiers.Classifier;

public class ParkingClassifier
  extends Classifier {

  /**
   * Returns only the toString() method.
   *
   * @return a string describing the classifier
   */
  public String globalInfo() {
    return toString();
  }

  /**
   * Returns the capabilities of this classifier.
   *
   * @return the capabilities
   */
  public Capabilities getCapabilities() {
    weka.core.Capabilities result = new weka.core.Capabilities(this);

    result.enable(weka.core.Capabilities.Capability.NOMINAL_ATTRIBUTES);
    result.enable(weka.core.Capabilities.Capability.NUMERIC_ATTRIBUTES);
    result.enable(weka.core.Capabilities.Capability.DATE_ATTRIBUTES);
    result.enable(weka.core.Capabilities.Capability.MISSING_VALUES);
    result.enable(weka.core.Capabilities.Capability.NOMINAL_CLASS);
    result.enable(weka.core.Capabilities.Capability.MISSING_CLASS_VALUES);

    result.setMinimumNumberInstances(0);

    return result;
  }

  /**
   * only checks the data against its capabilities.
   *
   * @param i the training data
   */
  public void buildClassifier(Instances i) throws Exception {
    // can classifier handle the data?
    getCapabilities().testWithFail(i);
  }

  /**
   * Classifies the given instance.
   *
   * @param i the instance to classify
   * @return the classification result
   */
  public double classifyInstance(Instance i) throws Exception {
    Object[] s = new Object[i.numAttributes()];
    
    for (int j = 0; j < s.length; j++) {
      if (!i.isMissing(j)) {
        if (i.attribute(j).isNominal())
          s[j] = new String(i.stringValue(j));
        else if (i.attribute(j).isNumeric())
          s[j] = new Double(i.value(j));
      }
    }
    
    // set class value to missing
    s[i.classIndex()] = null;
    
    return WekaClassifier.classify(s);
  }

  /**
   * Returns the revision string.
   * 
   * @return        the revision
   */
  public String getRevision() {
    return RevisionUtils.extract("1.0");
  }

  /**
   * Returns only the classnames and what classifier it is based on.
   *
   * @return a short description
   */
  public String toString() {
    return "Auto-generated classifier wrapper, based on weka.classifiers.trees.J48 (generated with Weka 3.6.13).\n" + this.getClass().getName() + "/WekaClassifier";
  }

  /**
   * Runs the classfier from commandline.
   *
   * @param args the commandline arguments
   */
  public static void main(String args[]) {
    runClassifier(new ParkingClassifier(), args);
  }
}

class WekaClassifier {

  public static double classify(Object[] i)
    throws Exception {

    double p = Double.NaN;
    p = WekaClassifier.N53b81d370(i);
    return p;
  }
  static double N53b81d370(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 17.512496595308043) {
    p = WekaClassifier.N472eddd1(i);
    } else if (((Double) i[3]).doubleValue() > 17.512496595308043) {
    p = WekaClassifier.N5ec895f820(i);
    } 
    return p;
  }
  static double N472eddd1(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 35.0) {
    p = WekaClassifier.N2e14677f2(i);
    } else if (((Double) i[0]).doubleValue() > 35.0) {
    p = WekaClassifier.Nf87dcab12(i);
    } 
    return p;
  }
  static double N2e14677f2(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 0.0) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() > 0.0) {
    p = WekaClassifier.N3f16526e3(i);
    } 
    return p;
  }
  static double N3f16526e3(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 10.926089452731421) {
    p = WekaClassifier.N2c54a11e4(i);
    } else if (((Double) i[2]).doubleValue() > 10.926089452731421) {
    p = WekaClassifier.N54775ba09(i);
    } 
    return p;
  }
  static double N2c54a11e4(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 8.169744479804251) {
    p = WekaClassifier.N3fdbbd4c5(i);
    } else if (((Double) i[3]).doubleValue() > 8.169744479804251) {
      p = 0;
    } 
    return p;
  }
  static double N3fdbbd4c5(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 13.0) {
    p = WekaClassifier.N32f91a576(i);
    } else if (((Double) i[0]).doubleValue() > 13.0) {
      p = 1;
    } 
    return p;
  }
  static double N32f91a576(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 2.0) {
    p = WekaClassifier.N3ff1ab447(i);
    } else if (((Double) i[0]).doubleValue() > 2.0) {
      p = 0;
    } 
    return p;
  }
  static double N3ff1ab447(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 3.87215332910998) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() > 3.87215332910998) {
    p = WekaClassifier.N3624a1f58(i);
    } 
    return p;
  }
  static double N3624a1f58(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 5.65568601451657) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 5.65568601451657) {
      p = 0;
    } 
    return p;
  }
  static double N54775ba09(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 30.0) {
    p = WekaClassifier.N2e26c8db10(i);
    } else if (((Double) i[0]).doubleValue() > 30.0) {
      p = 0;
    } 
    return p;
  }
  static double N2e26c8db10(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 11.691328078265304) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 11.691328078265304) {
    p = WekaClassifier.N1c3ffdd011(i);
    } 
    return p;
  }
  static double N1c3ffdd011(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.779876175743957) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.779876175743957) {
      p = 0;
    } 
    return p;
  }
  static double Nf87dcab12(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 9.273381849827281) {
    p = WekaClassifier.N61433ca013(i);
    } else if (((Double) i[2]).doubleValue() > 9.273381849827281) {
    p = WekaClassifier.N79bb504314(i);
    } 
    return p;
  }
  static double N61433ca013(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 55.0) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 55.0) {
      p = 1;
    } 
    return p;
  }
  static double N79bb504314(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 16.257292157961157) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() > 16.257292157961157) {
    p = WekaClassifier.N1a89f3d915(i);
    } 
    return p;
  }
  static double N1a89f3d915(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 12.0) {
    p = WekaClassifier.N2b5a06a716(i);
    } else if (((Double) i[1]).doubleValue() > 12.0) {
      p = 0;
    } 
    return p;
  }
  static double N2b5a06a716(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 12.29379638936823) {
    p = WekaClassifier.N5304995017(i);
    } else if (((Double) i[2]).doubleValue() > 12.29379638936823) {
      p = 0;
    } 
    return p;
  }
  static double N5304995017(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 16.374120032026877) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 16.374120032026877) {
    p = WekaClassifier.N709ef23b18(i);
    } 
    return p;
  }
  static double N709ef23b18(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 17.382221116863565) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() > 17.382221116863565) {
    p = WekaClassifier.N255cd0ca19(i);
    } 
    return p;
  }
  static double N255cd0ca19(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 17.476759306650806) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 17.476759306650806) {
      p = 1;
    } 
    return p;
  }
  static double N5ec895f820(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 1.0) {
    p = WekaClassifier.N29f1496821(i);
    } else if (((Double) i[1]).doubleValue() > 1.0) {
    p = WekaClassifier.N6de026c223(i);
    } 
    return p;
  }
  static double N29f1496821(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 0.0) {
    p = WekaClassifier.N2e79794722(i);
    } else if (((Double) i[1]).doubleValue() > 0.0) {
      p = 0;
    } 
    return p;
  }
  static double N2e79794722(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 5.0) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 5.0) {
      p = 1;
    } 
    return p;
  }
  static double N6de026c223(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 10.894284834571902) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() > 10.894284834571902) {
    p = WekaClassifier.N779d19d324(i);
    } 
    return p;
  }
  static double N779d19d324(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 13.0) {
    p = WekaClassifier.N5404a44125(i);
    } else if (((Double) i[1]).doubleValue() > 13.0) {
      p = 0;
    } 
    return p;
  }
  static double N5404a44125(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 24.063611031549478) {
    p = WekaClassifier.N31525cc726(i);
    } else if (((Double) i[3]).doubleValue() > 24.063611031549478) {
    p = WekaClassifier.N74229c2134(i);
    } 
    return p;
  }
  static double N31525cc726(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 37.0) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 37.0) {
    p = WekaClassifier.N5de8aa1927(i);
    } 
    return p;
  }
  static double N5de8aa1927(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 55.0) {
    p = WekaClassifier.N422463f628(i);
    } else if (((Double) i[0]).doubleValue() > 55.0) {
    p = WekaClassifier.N454b6bbd33(i);
    } 
    return p;
  }
  static double N422463f628(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 1.4145812354843401) {
    p = WekaClassifier.N3373e15f29(i);
    } else if (((Double) i[4]).doubleValue() > 1.4145812354843401) {
      p = 0;
    } 
    return p;
  }
  static double N3373e15f29(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.7396298595854719) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() > 0.7396298595854719) {
    p = WekaClassifier.N10e4379230(i);
    } 
    return p;
  }
  static double N10e4379230(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 22.267031210258132) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() > 22.267031210258132) {
    p = WekaClassifier.N8c4eff531(i);
    } 
    return p;
  }
  static double N8c4eff531(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 7.0) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() > 7.0) {
    p = WekaClassifier.N699e16c832(i);
    } 
    return p;
  }
  static double N699e16c832(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 10.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 10.0) {
      p = 1;
    } 
    return p;
  }
  static double N454b6bbd33(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 18.158950481171548) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() > 18.158950481171548) {
      p = 0;
    } 
    return p;
  }
  static double N74229c2134(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.6350854474591352) {
    p = WekaClassifier.N5b366e1735(i);
    } else if (((Double) i[4]).doubleValue() > 0.6350854474591352) {
      p = 0;
    } 
    return p;
  }
  static double N5b366e1735(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.5608717774319294) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.5608717774319294) {
      p = 0;
    } 
    return p;
  }
}
