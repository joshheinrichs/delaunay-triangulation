#delaunay-triangulation [![Build Status](https://magnum.travis-ci.com/Decateron/delaunay-triangulation.svg?token=ZMKSt61qYsroKf5FBCgc&branch=master)](https://magnum.travis-ci.com/Decateron/delaunay-triangulation)

An application built for real-time rendering and analysis of Delaunay triangulations made with JavaFX 8. This program was written as part of a undergraduate research project funded by the University of Saskatchewan under the guidance of Dr. Keil.

This program supports most edge cases, though there currently isn't support for displaying multiple versions of non-unique Delaunay triangulations. The algorithm is also inefficient (O(n^4) complexity), so the program will slow down quickly above 50 or so points.
