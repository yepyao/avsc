# import numpypy
import sys
from math import *

if __name__ == '__main__':
    if len(sys.argv) != 4:
        print 'usage: <config> <bufname> <Makefile>'
        sys.exit(1)
    mode = 0
    gd = []
    gl = []
    gi = []
    gu = []
    gfd = []
    for l in open(sys.argv[1]):
        if l.strip().startswith('#'):
            continue
        arr = l.split()
        for line in arr:
            if line.strip() == '-gd':
                mode = 0
                continue
            if line.strip() == '-g':
                mode = 1
                continue
            if line.strip() == '-i':
                mode = 2
                continue
            if line.strip() == '-efd':
                mode = 3
                continue
            if line.strip() == '-u':
                mode = 4
                continue

            s = line.strip()
            if s == '':
                continue
            
            if mode == 0:
                gd.append( s ) 
            elif mode == 1:
                gl.append( s )
            elif mode == 2:
                gi.append( s )
            elif mode == 3:
                gfd.append( s )
            else:
                gu.append( s )

    print 'include %s' % sys.argv[3]
    lst = [ 'train', 'test']
    for fn in lst:
        ggfd = []
        for i in gfd:
            if i.startswith( 'sns_train.') and fn == 'train':
                ggfd.append( 'sns.%s' % i[10:len(i)] )
            else:
                ggfd.append( i )
        
        dep = ' %s.txt %s.txt.imfb' % (fn,fn)  
        dep += ' '.join( [('features/%s.txt.%s' % (fn,item)) for item in gd]) +' '
        dep += ' '.join( [('features/%s.txt.%s' % (fn,item)) for item in gl]) +' '
        dep += ' '.join( [('features/%s.txt.%s' % (fn,item)) for item in gu]) +' '
        dep += ' '.join( [('features/%s.txt.%s' % (fn,item)) for item in gi]) +' '
        dep += ' '.join( [('features/%s.txt.%s' % (fn,item)) for item in ggfd])+' '
        arg = ' -gd '+ ' '.join(gd) + ' -g '+ ' '.join(gl)+' ' + ' -i '+ ' '.join(gi)+' ' + ' -efd '+ ' '.join(ggfd)+' ' + ' -u '+ ' '.join(gu)+' '
        print ('%s.%s.buffer:' % ('model/'+fn,sys.argv[2])) + dep
        print '\tkddcup_combine_ugroup %s.txt %s.%s.buffer  %s -max_block 20000 -scale_score 1'%( fn, 'model/'+fn, sys.argv[2], arg )
