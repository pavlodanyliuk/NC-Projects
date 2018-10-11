
This repository consist of two projects: SortsAnalyzer and GrantsProject

SortAnalyzer: 
  (Tech: Reflection API, Apache POI, JUnit, JavaDocs)
  By Java reflection api program find classes annotated as @Sort and calls their makeSort method with automatically generated arrays as parameter to get statistics about sort performance. Forming statistic to Excel using Apache POI. The whole code is documented with JavaDocs. And covered with tests(JUnit).
  
GrantsProject:
  Main functionality is saving existing objects (Company, Employee, Manager, Developer, ...) using Oracle DB. But data must be saving in Metamodel concepts.
  Проект був задуманий як одна із робіт в навчальному центрі Netcracker. Собою він представляє реалізацію запису даних про деяку компанію в базу даних Oracle. База даних спроектована як метамодель (всього 4 таблиці). І метою даного проекту було з організувати з допомогою DAO та звичайного JDBC запис будь-якого об'єкту системи.
